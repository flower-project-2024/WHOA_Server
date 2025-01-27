package com.whoa.whoaserver.domain.flower.repository.ranking;

import com.whoa.whoaserver.domain.flower.dto.response.FlowerPopularityResponseDto;

import java.util.List;

public interface FlowerPopularityRepositoryCustom {

	List<FlowerPopularityResponseDto> findAllFlowerPopularityRanking();
}
