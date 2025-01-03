package com.whoa.whoaserver.domain.flower.service;

import com.whoa.whoaserver.domain.bouquet.repository.BouquetRepository;
import com.whoa.whoaserver.domain.flower.domain.Flower;
import com.whoa.whoaserver.domain.flower.dto.response.FlowerPopularityResponseDto;
import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.domain.flowerExpression.repository.FlowerExpressionRepository;
import com.whoa.whoaserver.global.exception.ExceptionCode;
import com.whoa.whoaserver.global.exception.WhoaException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowerRankingServiceV3 {
	private final BouquetRepository bouquetRepository;
	private final FlowerExpressionRepository flowerExpressionRepository;

	@Transactional(readOnly = true)
	@Cacheable(cacheNames = "flowerPopularity")
	public List<FlowerPopularityResponseDto> getFlowerPopularityRanking() {
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
		List<FlowerPopularityResponseDto> response = new ArrayList<>();
		for (Flower popularFlower : keySetFlowerExpressionIdList) {
			if (flowerRanking > 5) break;
			response.add(FlowerPopularityResponseDto.from(popularFlower, flowerRanking));
			flowerRanking++;
		}

		return response;
	}

	private FlowerExpression getFlowerExpressionByFlowerExpressionId(Long flowerExpressionId) {
		return flowerExpressionRepository.findById(flowerExpressionId)
			.orElseThrow(() -> new WhoaException(ExceptionCode.FLOWER_EXPRESSION_NOT_FOUND));
	}
}
