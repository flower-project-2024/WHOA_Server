package com.whoa.whoaserver.scheduler;

import com.whoa.whoaserver.domain.bouquet.repository.BouquetRepository;
import com.whoa.whoaserver.domain.flower.domain.Flower;
import com.whoa.whoaserver.domain.flower.domain.FlowerPopularity;
import com.whoa.whoaserver.domain.flower.dto.response.FlowerPopularityResponseDto;
import com.whoa.whoaserver.domain.flower.repository.ranking.FlowerPopularityRepository;
import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.domain.flowerExpression.repository.FlowerExpressionRepository;
import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.global.exception.WhoaException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FlowerPopularityScheduler {

	private final BouquetRepository bouquetRepository;
	private final FlowerExpressionRepository flowerExpressionRepository;
	private final FlowerPopularityRepository flowerPopularityRepository;

	@Scheduled(cron = "0 0 0 * * MON")
	public void calculateFlowerPopularity() {
		List<String> allFlowerTypes = bouquetRepository.findAllFlowerTypeInformation();

		HashMap<Flower, Integer> allSelectedFlowerMapByFlowerExpressionIdsOfBouquet = new HashMap<>();
		allFlowerTypes.forEach(flowerTypeCol -> {
			String[] selectedFlowerExpressionOfEachBouquet = flowerTypeCol.split(", ");
			for (String flowerExpressionId : selectedFlowerExpressionOfEachBouquet) {
				Flower flower = getFlowerExpressionByFlowerExpressionId(Long.valueOf(flowerExpressionId)).getFlower();
				if (!allSelectedFlowerMapByFlowerExpressionIdsOfBouquet.containsKey(flower)) {
					allSelectedFlowerMapByFlowerExpressionIdsOfBouquet.put(flower, 0);
				}

				Integer frequency = allSelectedFlowerMapByFlowerExpressionIdsOfBouquet.get(flower);
				allSelectedFlowerMapByFlowerExpressionIdsOfBouquet.put(flower, frequency + 1);
			}
		});

		List<Flower> keySetFlowerExpressionIdList = new ArrayList<>(allSelectedFlowerMapByFlowerExpressionIdsOfBouquet.keySet());
		Collections.sort(keySetFlowerExpressionIdList, (o1, o2) -> (allSelectedFlowerMapByFlowerExpressionIdsOfBouquet.get(o2).compareTo(allSelectedFlowerMapByFlowerExpressionIdsOfBouquet.get(o1))));

		int flowerRanking = 1;
		if (flowerPopularityRepository.count() == 0L) {
			for(Flower popularFlower : keySetFlowerExpressionIdList) {
				if (flowerRanking > 5) break;
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
			for(Flower popularFlower : keySetFlowerExpressionIdList) {
				if (flowerRanking > 5) break;
				FlowerPopularity existingFlowerPopularity = flowerPopularityRepository.findByFlowerRanking(flowerRanking);
				existingFlowerPopularity.updateFlowerPopularity(
					popularFlower.getFlowerId(),
					getFlowerImageUrlByFlower(popularFlower),
					flowerRanking,
					popularFlower.getFlowerName(),
					getFlowerExpressionByFlower(popularFlower)
				);
				flowerPopularityRepository.save(existingFlowerPopularity);
				flowerRanking++;
			}
		}

	}

	private FlowerExpression getFlowerExpressionByFlowerExpressionId(Long flowerExpressionId) {
		return flowerExpressionRepository.findById(flowerExpressionId)
			.orElseThrow(() -> new WhoaException(ExceptionCode.FLOWER_EXPRESSION_NOT_FOUND));
	}

	private String getFlowerImageUrlByFlower(Flower flower) {
		return (flower.getFlowerImages() == null)? "" : flower.getFlowerImages().get(0).getImageUrl();
	}

	private String getFlowerExpressionByFlower(Flower flower) {
		return (flower.getFlowerExpressions() == null)? "" : flower.getFlowerExpressions().get(0).getFlowerLanguage();
	}

}
