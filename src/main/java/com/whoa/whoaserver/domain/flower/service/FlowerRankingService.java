package com.whoa.whoaserver.domain.flower.service;


import com.whoa.whoaserver.domain.flower.dto.response.FlowerRankingResponseDto;
import com.whoa.whoaserver.domain.flower.repository.ranking.FlowerRankingRepository;
import com.whoa.whoaserver.domain.flower.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowerRankingService {

	final FlowerRankingRepository flowerRankingRepository;
	final FlowerRepository flowerRepository;

	@Transactional
	public List<FlowerRankingResponseDto> getFlowerRanking() {
		return flowerRankingRepository.findAllFlowerRankingInformation();
	}

}
