package com.whoa.whoaserver.bouquet.dto.response;

import com.whoa.whoaserver.bouquet.domain.Bouquet;
import com.whoa.whoaserver.bouquet.domain.BouquetImage;

public record RealBouquetImageResponse(
	String imgUrl
) {
	public static RealBouquetImageResponse from(Bouquet bouquet) {
		return new RealBouquetImageResponse(bouquet.getRealImageUrl());
	}
}
