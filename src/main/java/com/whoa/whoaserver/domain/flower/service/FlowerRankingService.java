package com.whoa.whoaserver.domain.flower.service;


import com.whoa.whoaserver.domain.flower.dto.response.FlowerRankingResponseDto;
import com.whoa.whoaserver.domain.flower.repository.ranking.FlowerRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowerRankingService {

	final FlowerRankingRepository flowerRankingRepository;

	@Transactional
	public List<FlowerRankingResponseDto> getFlowerRanking() {
		return flowerRankingRepository.findAllFlowerRankingInformation();
	}

}
