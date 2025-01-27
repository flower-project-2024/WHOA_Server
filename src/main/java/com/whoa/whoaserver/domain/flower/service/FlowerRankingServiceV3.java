package com.whoa.whoaserver.domain.flower.service;

import com.whoa.whoaserver.domain.flower.dto.response.FlowerPopularityResponseDto;
import com.whoa.whoaserver.domain.flower.repository.ranking.FlowerPopularityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowerRankingServiceV3 {

	private final FlowerPopularityRepository flowerPopularityRepository;

	public List<FlowerPopularityResponseDto> getFlowerPopularityRanking() {
		return flowerPopularityRepository.findTop5FlowerPopularityRanking();
	}
}
