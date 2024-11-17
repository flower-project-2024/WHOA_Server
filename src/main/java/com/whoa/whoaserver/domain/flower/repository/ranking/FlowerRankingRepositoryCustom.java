package com.whoa.whoaserver.domain.flower.repository.ranking;

import com.whoa.whoaserver.domain.flower.dto.response.FlowerRankingResponseDto;

import java.util.List;

public interface FlowerRankingRepositoryCustom {
	List<FlowerRankingResponseDto> findAllFlowerRankingInformation();
}
