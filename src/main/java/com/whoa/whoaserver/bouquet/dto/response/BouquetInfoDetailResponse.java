package com.whoa.whoaserver.bouquet.dto.response;

import com.whoa.whoaserver.bouquet.domain.Bouquet;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                flowerInfoList
        );
    }
}
