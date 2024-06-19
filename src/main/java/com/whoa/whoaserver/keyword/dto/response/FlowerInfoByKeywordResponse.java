package com.whoa.whoaserver.keyword.dto.response;

import com.whoa.whoaserver.flower.domain.FlowerImage;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;

import java.util.List;

public record FlowerInfoByKeywordResponse(
        String flowerName,
        String flowerLanguage,
        String flowerImageUrl,
        List<String> flowerKeyword
) {

    public static FlowerInfoByKeywordResponse fromFlowerExpressionAndKeyword(FlowerExpression flowerExpression, FlowerImage flowerImage, List<String> flowerKeyword) {
        String imageUrl = (flowerImage != null) ? flowerImage.getImageUrl() : "";

        return new FlowerInfoByKeywordResponse(
                flowerExpression.getFlower().getFlowerName(),
                flowerExpression.getFlowerLanguage(),
                imageUrl,
                flowerKeyword
        );
    }

}
