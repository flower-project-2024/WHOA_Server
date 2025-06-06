package com.whoa.whoaserver.scheduler;

import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.global.exception.WhoaException;
import com.whoa.whoaserver.scheduler.dto.WebClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.whoa.whoaserver.global.utils.LoggerUtils.logger;

@Component
@RequiredArgsConstructor
public class FlowerCrawlerScheduler {

	private static final String FLOWER_INFORMATION_DEFAULT_ADDRESS = "https://flower.at.or.kr/api/returnData.api?";
	private static final String KIND_PARAM = "kind=f001";
	private static final String SERVICE_KEY_PARAM = "serviceKey=";
	private static final String BASE_DATE_PARAM = "baseDate=";
	private static final String FLOWER_GUBN_PARAM = "flowerGubn=1";
	private static final String DATA_TYPE_PARAM = "dataType=json";


	@Value("${crawl.service-key}")
	private String serviceKey;

	private final FlowerRankingUpdater flowerRankingUpdater;

	@Scheduled(cron = "0 0 0 * * MON")
	public void crawlFlowerData() {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = currentDate.format(formatter);
		String apiUrl = String.format("%s%s&%s%s&%s%s&%s&%s",
			FLOWER_INFORMATION_DEFAULT_ADDRESS, KIND_PARAM, SERVICE_KEY_PARAM, serviceKey,
			BASE_DATE_PARAM, formattedDate, FLOWER_GUBN_PARAM, DATA_TYPE_PARAM);

		logger.info("{} 데이터 요청 시작", apiUrl);

		WebClient webClient = WebClient.builder().baseUrl(apiUrl).build();

		try {
			WebClientResponse response = webClient.get()
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError,
					clientResponse -> Mono.error(new WhoaException(ExceptionCode.SCHEDULER_CLIENT_REQUEST_ERROR)))
				.onStatus(HttpStatusCode::is5xxServerError,
					clientResponse -> Mono.error(new WhoaException(ExceptionCode.SCHEDULER_FLOWER_SERVER_ERROR)))
				.bodyToMono(WebClientResponse.class)
				.block();

			if (response != null && response.getResponse() != null) {
				logger.info("외부 API 호출 응답 : {}", response.getResponse());
				processResponseData(response.getResponse(), formattedDate);
			} else {
				logger.error("외부 API Response 응답 실패");
			}

		} catch (WhoaException e) {
			throw new WhoaException(ExceptionCode.SCHEDULER_WHOA_SERVER_ERROR);
		}
	}

	private void processResponseData(WebClientResponse.Response response, String formattedDate) {
		String resultMsg = response.getResultMsg();
		int numOfRows = response.getNumOfRows();

		if ("OK".equals(resultMsg) && numOfRows >= 5) {
			logger.info("외부 API Response로 flowerRanking table에 새로운 data update 가능");

			List<WebClientResponse.Item> items = List.of(response.getItems());
			logger.info("immutable items data : {}", items);

			List<WebClientResponse.Item> mutableItems = new ArrayList<>(items);
			mutableItems.sort(Comparator.comparingInt(item -> Integer.parseInt(item.getAvgAmt())));
			logger.info("mutable items sorted data : {}", mutableItems);

			Set<String> savedNames = new HashSet<>();
			long flowerRankingId = 0;

			for (WebClientResponse.Item item : mutableItems) {
				String flowerName = item.getPumName();
				String flowerPrice = item.getAvgAmt();
				logger.info("DB에 저장될 flowerName : {}, flowerPrice : {}", flowerName, flowerPrice);

				if (!savedNames.contains(flowerName)) {
					savedNames.add(flowerName);
					flowerRankingId++;
					flowerRankingUpdater.updateFlowerRanking(flowerRankingId, flowerName, flowerPrice, formattedDate);
				}
				if (flowerRankingId == 5) {
					logger.info("disorderly added savedNames : {}", savedNames);
					break;
				}
			}
		} else {
			logger.info("외부 API Response 정상적으로 얻었으나 새로운 데이터가 없어 기존 데이터 유지 및 date만 변경");
			flowerRankingUpdater.updateOnlyFlowerRankingDateToFormattedDate(formattedDate);
		}
	}
}
