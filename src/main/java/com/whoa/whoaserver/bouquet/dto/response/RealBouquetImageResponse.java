package com.whoa.whoaserver.bouquet.dto.response;

import com.whoa.whoaserver.bouquet.domain.BouquetImage;

public record RealBouquetImageResponse(
	String imgUrl
) {
	public static RealBouquetImageResponse from(BouquetImage bouquetImage) {
		return new RealBouquetImageResponse(bouquetImage.getFileName());
	}
}
