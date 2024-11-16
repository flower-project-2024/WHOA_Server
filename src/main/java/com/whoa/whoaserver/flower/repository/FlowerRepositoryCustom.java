package com.whoa.whoaserver.flower.repository;

import com.whoa.whoaserver.flower.dto.FlowerSearchResponseDto;

public interface FlowerRepositoryCustom {
	FlowerSearchResponseDto findAllFlowers(Long flowerId);
}
