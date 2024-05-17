package com.whoa.whoaserver.bouquet.dto.response;

import com.whoa.whoaserver.bouquet.domain.Bouquet;
import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flower.repository.FlowerRepository;
import com.whoa.whoaserver.flower.utils.FlowerUtils;
import com.whoa.whoaserver.keyword.domain.Keyword;
import com.whoa.whoaserver.keyword.repository.FlowerKeywordRepository;

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
        String wrappingType,
        String priceRange,
        String requirement,
        List<String> imagePaths,
        List<HashMap<String, String>> flowerInfoList // Flower Name, Flower Image, Keyword Name

) {
    public static BouquetInfoDetailResponse of(Bouquet bouquet, FlowerRepository flowerRepository) {

        List<String> flowerTypes = FlowerUtils.parseFlowerType(bouquet.getFlowerType());
        List<HashMap<String, String>> flowerInfoList = new ArrayList<>();

        for (String flowerType : flowerTypes) {
            Flower flower = flowerRepository.findByFlowerName(flowerType);
            HashMap<String, String> flowerInfo = new HashMap<>();

            if (flower != null) {
                flowerInfo.put("name", flower.getFlowerName());
                flowerInfo.put("imageUrl", flower.getFlowerImages().get(0));

                Keyword keyword = flower.getKeyword();

                if (keyword != null) {
                    flowerInfo.put("flowerLanguage", keyword.getKeywordName());
                }
            }
            flowerInfoList.add(flowerInfo);
        }
        return new BouquetInfoDetailResponse(
                bouquet.getId(),
                bouquet.getPurpose(),
                bouquet.getColorType().getValue(),
                bouquet.getColorName(),
                bouquet.getPointColor(),
                bouquet.getWrappingType(),
                bouquet.getPriceRange(),
                bouquet.getRequirement(),
                bouquet.getImages().stream().map(bouquetImage -> bouquetImage.getFileName())
                        .collect(Collectors.toList()),
                flowerInfoList
        );
    }
}
