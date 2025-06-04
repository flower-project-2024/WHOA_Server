package com.whoa.whoaserver.scheduler;

import com.whoa.whoaserver.domain.bouquet.repository.BouquetRepository;
import com.whoa.whoaserver.domain.flower.domain.Flower;
import com.whoa.whoaserver.domain.flower.domain.FlowerPopularity;
import com.whoa.whoaserver.domain.flower.repository.ranking.FlowerPopularityRepository;
import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.domain.flowerExpression.repository.FlowerExpressionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.whoa.whoaserver.global.utils.LoggerUtils.logger;

@Component
@RequiredArgsConstructor
public class FlowerPopularityScheduler {

	private final BouquetRepository bouquetRepository;
	private final FlowerExpressionRepository flowerExpressionRepository;
	private final FlowerPopularityRepository flowerPopularityRepository;

	@Scheduled(cron = "0 0 0 * * MON")
	@Transactional
	public void calculateFlowerPopularity() {
		List<String> allFlowerTypes = bouquetRepository.findAllFlowerTypeInformation();

		HashMap<Flower, Integer> allSelectedFlowerMapByFlowerExpressionIdsOfBouquet = new HashMap<>();
		allFlowerTypes.forEach(flowerTypeCol -> {
			long[] selectedFlowerExpressionIdsOfEachBouquet = Arrays.stream(flowerTypeCol.split(", ")).mapToLong(Long::parseLong).toArray();
			List<FlowerExpression> selectedFlowerExpression = flowerExpressionRepository.findByFlowerExpressionIdIn(selectedFlowerExpressionIdsOfEachBouquet);
			for (FlowerExpression flowerExpression : selectedFlowerExpression) {
				Flower flower = flowerExpression.getFlower();
				allSelectedFlowerMapByFlowerExpressionIdsOfBouquet.put(
					flower,
					allSelectedFlowerMapByFlowerExpressionIdsOfBouquet.getOrDefault(flower, 0) + 1
				);
			}
		});

		List<Flower> flowerIdList = new ArrayList<>(allSelectedFlowerMapByFlowerExpressionIdsOfBouquet.keySet());
		flowerIdList.sort((o1, o2) -> (allSelectedFlowerMapByFlowerExpressionIdsOfBouquet.get(o2).compareTo(allSelectedFlowerMapByFlowerExpressionIdsOfBouquet.get(o1))));

		int flowerRanking = 1;
		if (flowerPopularityRepository.count() == 0L) {
			for (Flower popularFlower : flowerIdList) {
				FlowerPopularity newFlowerPopularity = FlowerPopularity.initializeFlowerPopularityRanking(
					popularFlower.getFlowerId(),
					getFlowerImageUrlByFlower(popularFlower),
					flowerRanking,
					popularFlower.getFlowerName(),
					getFlowerExpressionByFlower(popularFlower)
				);
				flowerPopularityRepository.save(newFlowerPopularity);
				flowerRanking++;
			}
		} else {
			ArrayList<FlowerPopularity> flowerPopularityList = new ArrayList<>();
			for (Flower popularFlower : flowerIdList) {
				FlowerPopularity existingFlowerPopularity = flowerPopularityRepository.findByFlowerId(popularFlower.getFlowerId())
					.orElse(null);
				if (existingFlowerPopularity == null) {
					logger.info("flowerId -> {} : 새로운 꽃 선택으로 flowerPopularity에 추가해야 하는 상황", popularFlower.getFlowerId());
					FlowerPopularity newFlowerPopularity = FlowerPopularity.initializeFlowerPopularityRanking(
						popularFlower.getFlowerId(),
						getFlowerImageUrlByFlower(popularFlower),
						flowerRanking,
						popularFlower.getFlowerName(),
						getFlowerExpressionByFlower(popularFlower)
					);
					flowerPopularityList.add(newFlowerPopularity);
				} else {
					Integer lastWeekRank = existingFlowerPopularity.getFlowerRanking();
					existingFlowerPopularity.updateFlowerPopularity(
						flowerRanking, lastWeekRank - flowerRanking
					);
					flowerPopularityList.add(existingFlowerPopularity);
				}


				flowerRanking++;
			}
			flowerPopularityRepository.saveAll(flowerPopularityList);
		}

	}

	private String getFlowerImageUrlByFlower(Flower flower) {
		return (flower.getFlowerImages() == null || flower.getFlowerImages().isEmpty()) ? "" : flower.getFlowerImages().get(0).getImageUrl();
	}

	private String getFlowerExpressionByFlower(Flower flower) {
		return (flower.getFlowerExpressions() == null || (flower.getFlowerExpressions().isEmpty())) ? "" : flower.getFlowerExpressions().get(0).getFlowerLanguage();
	}

}
