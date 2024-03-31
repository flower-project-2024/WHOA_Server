package com.whoa.whoaserver.bouquet.dto.response;

import com.whoa.whoaserver.bouquet.domain.Bouquet;

import java.util.List;
import java.util.stream.Collectors;

public record BouquetInfoDetailResponse(
        Long id,
        String purpose,
        String colorType,
        String flowerType,
        String wrappingType,
        String priceRange,
        String requirement,
        List<String> imagePaths

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
                bouquet.getImages().stream().map(bouquetImage -> bouquetImage.getFileName())
                        .collect(Collectors.toList())
        );
    }
}
