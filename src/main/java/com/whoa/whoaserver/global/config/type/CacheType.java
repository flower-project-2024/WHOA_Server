package com.whoa.whoaserver.global.config.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CacheType {
	FLOWER_INFORMATION_BY_KEYWORD("keyword", 12, 200);

	private final String cacheName;
	private final int expiredAfterWrite; // hour
	private final int maximumSize; // dto count
}