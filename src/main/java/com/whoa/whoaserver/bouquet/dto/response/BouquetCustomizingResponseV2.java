package com.whoa.whoaserver.bouquet.dto.response;

import com.whoa.whoaserver.bouquet.domain.Bouquet;

import java.util.List;

public record BouquetCustomizingResponseV2 (
	Long bouquetId,
	List<String> imgList
) {
	public static BouquetCustomizingResponseV2 of(Bouquet bouquet, List<String> imgList) {
		return new BouquetCustomizingResponseV2(bouquet.getId(), imgList);
	}
}
