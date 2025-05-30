package com.whoa.whoaserver.domain.keyword.dto.response;

import com.whoa.whoaserver.domain.flower.domain.FlowerImage;
import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import lombok.Builder;

import java.util.List;

@Builder
public record FlowerInfoByKeywordResponse(
        Long flowerExpressionId,
        String flowerName,
        String flowerLanguage,
        String flowerImageUrl,
        List<String> flowerKeyword
) {

    public static FlowerInfoByKeywordResponse fromFlowerExpressionAndKeyword(FlowerExpression flowerExpression, FlowerImage flowerImage, List<String> flowerKeyword) {
        String imageUrl = (flowerImage != null) ? flowerImage.getImageUrl() : "";

        return new FlowerInfoByKeywordResponse(
                flowerExpression.getFlowerExpressionId(),
                flowerExpression.getFlower().getFlowerName(),
                flowerExpression.getFlowerLanguage(),
                imageUrl,
                flowerKeyword
        );
    }

}
