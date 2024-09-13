package com.whoa.whoaserver.keyword.dto.response;

import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;

public record FlowerInfoByKeywordResponseV2(
	Long id,
	String flowerName,
	String flowerLanguage,
	String flowerImageUrl
) {
	public static FlowerInfoByKeywordResponseV2 from(FlowerExpression flowerExpression) {
		return new FlowerInfoByKeywordResponseV2(
			flowerExpression.getFlowerExpressionId(),
			flowerExpression.getFlower().getFlowerName(),
			flowerExpression.getFlowerLanguage(),
			flowerExpression.getFlowerImage().getImageUrl()
		);
	}
}
