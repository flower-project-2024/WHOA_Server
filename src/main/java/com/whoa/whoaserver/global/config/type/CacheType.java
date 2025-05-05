package com.whoa.whoaserver.global.config.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {
	FLOWER_INFORMATION_BY_KEYWORD("keyword", 12, 200),
	FLOWER_INFORMATION_BY_KEYWORD_AND_CUSTOMIZING_PURPOSE("CustomizingPurposeAndKeyword", 12, 200),
	FLOWER_IMAGE_BY_FLOWER_EXPRESSION("flowerImage", 12, 200),
	FLOWER_POPULARITY_RANKING("flowerPopularity", 12, 200),
	FLOWER_SEARCH_INFORMATION_ID_AND_NAME("flowerSearch", 168, 200);

	public static final String FLOWER_SEARCH_CACHE_NAME = "flowerSearch";

	private final String cacheName;
	private final int expiredAfterWrite; // hour
	private final int maximumSize; // dto count
}
