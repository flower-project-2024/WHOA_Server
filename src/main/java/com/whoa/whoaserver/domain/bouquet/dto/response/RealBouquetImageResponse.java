package com.whoa.whoaserver.domain.bouquet.dto.response;

import com.whoa.whoaserver.domain.bouquet.domain.Bouquet;

public record RealBouquetImageResponse(
	String imgUrl
) {
	public static RealBouquetImageResponse from(Bouquet bouquet) {
		return new RealBouquetImageResponse(bouquet.getRealImageUrl());
	}
}
