package com.whoa.whoaserver.bouquet.dto.response;

import com.whoa.whoaserver.bouquet.domain.Bouquet;

public record BouquetCustomizingResponse(
    Long id
) {
    public static BouquetCustomizingResponse of(Bouquet bouquet) {
        return new BouquetCustomizingResponse(bouquet.getId());
    }
 }
