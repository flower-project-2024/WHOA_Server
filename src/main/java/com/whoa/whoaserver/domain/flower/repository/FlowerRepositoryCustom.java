package com.whoa.whoaserver.domain.flower.repository;

import com.whoa.whoaserver.domain.flower.domain.Flower;
import com.whoa.whoaserver.domain.flower.dto.response.FlowerSearchResponseDto;

import java.util.List;

public interface FlowerRepositoryCustom {
	List<FlowerSearchResponseDto> findAllFlowers();

	Flower findRandomFlower();
}
