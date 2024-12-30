package com.whoa.whoaserver.domain.flower.dto.response;

import com.whoa.whoaserver.domain.flower.domain.Flower;

public record FlowerPopularityResponseDto(
	Long flowerId,
	String flowerImageUrl,
	Integer flowerRanking,
	String flowerName,
	String flowerLanguage
) {

	public static FlowerPopularityResponseDto from(Flower flower, Integer flowerRanking) {
		String imageUrl = (flower.getFlowerImages().isEmpty())? "" : flower.getFlowerImages().get(0).getImageUrl();
		String flowerLanguage = (flower.getFlowerExpressions().isEmpty())? "" : flower.getFlowerExpressions().get(0).getFlowerLanguage();

		return new FlowerPopularityResponseDto(
			flower.getFlowerId(),
			imageUrl,
			flowerRanking,
			flower.getFlowerName(),
			flowerLanguage
		);
	}
}
