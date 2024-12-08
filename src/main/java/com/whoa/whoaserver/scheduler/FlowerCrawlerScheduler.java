package com.whoa.whoaserver.scheduler;

import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.global.exception.WhoaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.whoa.whoaserver.scheduler.dto.WebClientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FlowerCrawlerScheduler {

	@Value("${crawl.service-key}")
	private String serviceKey;

	private final FlowerRankingUpdater flowerRankingUpdater;

	Logger logger = LoggerFactory.getLogger(FlowerCrawlerScheduler.class);

	@Scheduled(cron = "0 0 0 * * MON")
	public void crawlFlowerData() {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = currentDate.format(formatter);
		String apiUrl = "https://flower.at.or.kr/api/returnData.api?kind=f001&serviceKey=" + serviceKey + "&baseDate=" + formattedDate + "&flowerGubn=1&dataType=json";

		logger.info("스케쥴러 실행 시작");

		WebClient webClient = WebClient.builder().baseUrl(apiUrl).build();

		try {
			WebClientResponse response = webClient.get()
				.retrieve()
				.onStatus(status -> status.is4xxClientError(), clientResponse -> {
					return Mono.error(new WhoaException(ExceptionCode.SCHEDULER_CLIENT_REQUEST_ERROR));
				})
				.onStatus(status -> status.is5xxServerError(), clientResponse -> {
					return Mono.error(new WhoaException(ExceptionCode.SCHEDULER_FLOWER_SERVER_ERROR));
				})
				.bodyToMono(WebClientResponse.class)
				.block();

			if (response != null && response.getResponse() != null) {
				logger.info("외부 API 호출 응답 : {}", response.getResponse());
				processResponseData(response.getResponse(), formattedDate);
			} else {
				logger.error("외부 API Response 응답 실패");
			}

		} catch (RuntimeException e) {
			throw new RuntimeException("스케쥴러 동작 중 에러 발생", e);
		}
	}

	private void processResponseData(WebClientResponse.Response response, String formattedDate) {
		String resultMsg = response.getResultMsg();
		int numOfRows = response.getNumOfRows();

		if ("OK".equals(resultMsg) && numOfRows >= 5) {
			logger.info("외부 API Response로 flowerRanking table에 새로운 data update 가능");
			List<WebClientResponse.Item> items = List.of(response.getItems());

			items.sort(Comparator.comparingInt(item -> Integer.parseInt(item.getAvgAmt())));

			Set<String> savedNames = new HashSet<>();
			long flowerRankingId = 0;

			for (WebClientResponse.Item item : items) {
				String flowerName = item.getPumName();
				String flowerPrice = item.getAvgAmt();

				if (!savedNames.contains(flowerName)) {
					savedNames.add(flowerName);
					flowerRankingId++;
					flowerRankingUpdater.updateFlowerRanking(flowerRankingId, flowerName, flowerPrice, formattedDate);
				}
				if (flowerRankingId == 5) break;
			}
		} else {
			logger.info("외부 API Response 정상적으로 얻었으나 새로운 데이터가 없어 기존 데이터 유지 및 date만 변경");
			flowerRankingUpdater.updateOnlyFlowerRankingDateToFormattedDate(formattedDate);
		}
	}
}
