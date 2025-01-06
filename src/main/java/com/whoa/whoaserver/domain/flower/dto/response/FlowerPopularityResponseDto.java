package com.whoa.whoaserver.domain.flower.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class FlowerPopularityResponseDto {
	Long flowerId;
	String flowerImageUrl;
	Integer flowerRanking;
	String flowerName;
	String flowerLanguage;

	@QueryProjection
	public FlowerPopularityResponseDto(Long flowerId, String flowerImageUrl, Integer flowerRanking, String flowerName, String flowerLanguage) {
		this.flowerId = flowerId;
		this.flowerImageUrl = flowerImageUrl;
		this.flowerRanking = flowerRanking;
		this.flowerName = flowerName;
		this.flowerLanguage = flowerLanguage;
	}
}
