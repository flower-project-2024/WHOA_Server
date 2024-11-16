package com.whoa.whoaserver.flower.repository;

import com.whoa.whoaserver.flower.dto.FlowerSearchResponseDto;

import java.util.List;

public interface FlowerRepositoryCustom {
	List<FlowerSearchResponseDto> findAllFlowers();
}
