package com.whoa.whoaserver.domain.bouquet.dto.response;

import com.whoa.whoaserver.domain.bouquet.domain.Bouquet;
import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public record BouquetInfoDetailResponse(
        Long id,
        String purpose,
        String colorType,
        String colorName,
        String pointColor,
        String substitutionType,
        String wrappingType,
        String priceRange,
        String requirement,
        List<HashMap<String, String>> imgInfoList,
        List<HashMap<String, String>> flowerInfoList // Flower Name, Flower Image, Flower language

) {
    public static BouquetInfoDetailResponse of(Bouquet bouquet, List<FlowerExpression> flowerExpressionList) {

        List<HashMap<String, String>> flowerInfoList = new ArrayList<>();

        for (FlowerExpression flowerExpression : flowerExpressionList) {
            HashMap<String, String> flowerInfo = new HashMap<>();

            if (flowerExpression != null) {
                flowerInfo.put("flowerName", flowerExpression.getFlower().getFlowerName());

                String flowerImageUrl = (flowerExpression.getFlowerImage() != null) ? flowerExpression.getFlowerImage().getImageUrl() : "";
                flowerInfo.put("flowerImageUrl", flowerImageUrl);

                flowerInfo.put("flowerLanguage", flowerExpression.getFlowerLanguage());
            }
            flowerInfoList.add(flowerInfo);
        }

        List<HashMap<String, String>> imgInfoList = bouquet.getImages().stream()
                .map(bouquetImage -> {
                    HashMap<String, String> imgHash = new HashMap<>();
                    imgHash.put("bouquetImageId", bouquetImage.getId().toString());
                    imgHash.put("bouquetImageUrl", bouquetImage.getFileName());
                    return imgHash;
                })
                .collect(Collectors.toUnmodifiableList());


        return new BouquetInfoDetailResponse(
                bouquet.getId(),
                bouquet.getPurpose(),
                bouquet.getColorType().getValue(),
                bouquet.getColorName(),
                bouquet.getPointColor(),
                bouquet.getSubsitutionType().getValue(),
                bouquet.getWrappingType(),
                bouquet.getPriceRange(),
                bouquet.getRequirement(),
                imgInfoList,
                flowerInfoList
        );
    }
}
