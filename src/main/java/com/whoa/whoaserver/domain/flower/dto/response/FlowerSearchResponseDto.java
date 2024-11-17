package com.whoa.whoaserver.domain.flower.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class FlowerSearchResponseDto {
	private final Long flowerId;
	private final String flowerName;

	@QueryProjection
	public FlowerSearchResponseDto(Long flowerId, String flowerName) {
		this.flowerId = flowerId;
		this.flowerName = flowerName;
	}
}
