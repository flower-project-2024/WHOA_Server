package com.whoa.whoaserver.domain.bouquet.dto.response;

import com.whoa.whoaserver.domain.bouquet.domain.Bouquet;

public record BouquetCustomizingResponse(
	Long bouquetId
) {
	public static BouquetCustomizingResponse of(Bouquet bouquet) {
		return new BouquetCustomizingResponse(bouquet.getId());
	}
}
