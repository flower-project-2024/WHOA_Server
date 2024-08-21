package com.whoa.whoaserver.bouquet.dto.response;

import com.whoa.whoaserver.bouquet.domain.Bouquet;

import java.util.List;

public record BouquetCustomizingResponse(
    Long bouquetId,
	List<String> imgList
) {
    public static BouquetCustomizingResponse of(Bouquet bouquet, List<String> imgList) {
        return new BouquetCustomizingResponse(bouquet.getId(), imgList);
    }
 }
