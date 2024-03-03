package com.whoa.whoaserver.bouquet.dto.response;

import com.whoa.whoaserver.bouquet.domain.Bouquet;

public record BouquetInfoDetailResponse(
        Long id,
        String purpose,
        String colorType,
        String flowerType,
        String wrappingType,
        String priceRange,
        String requirement,
        String imagePath

) {
    public static BouquetInfoDetailResponse of(Bouquet bouquet) {
        return new BouquetInfoDetailResponse(
                bouquet.getId(),
                bouquet.getPurpose(),
                bouquet.getColorType(),
                bouquet.getFlowerType(),
                bouquet.getWrappingType(),
                bouquet.getPriceRange(),
                bouquet.getRequirement(),
                bouquet.getImagePath()
        );
    }
}
