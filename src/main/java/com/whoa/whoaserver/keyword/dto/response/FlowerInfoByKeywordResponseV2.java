package com.whoa.whoaserver.keyword.dto.response;

import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.keyword.domain.Keyword;
import com.whoa.whoaserver.mapping.domain.FlowerExpressionKeyword;

import java.util.*;
import java.util.stream.Collectors;

public record FlowerInfoByKeywordResponseV2(
	Long id,
	String flowerName,
	String flowerLanguage,
	String flowerImageUrl,
	List<String> keywords
) {
	public static FlowerInfoByKeywordResponseV2 from(FlowerExpression flowerExpression) {
		return new FlowerInfoByKeywordResponseV2(
			flowerExpression.getFlowerExpressionId(),
			flowerExpression.getFlower().getFlowerName(),
			flowerExpression.getFlowerLanguage(),
			flowerExpression.getFlowerImage().getImageUrl(),
			flowerExpression.getFlowerExpressionKeywords().stream()
				.map(FlowerExpressionKeyword::getKeyword).map(Keyword::getKeywordName).collect(Collectors.toUnmodifiableList())
		);
	}
}
