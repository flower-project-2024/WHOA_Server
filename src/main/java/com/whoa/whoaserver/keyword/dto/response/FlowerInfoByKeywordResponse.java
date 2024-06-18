package com.whoa.whoaserver.keyword.dto.response;

import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;

import java.util.List;

public record FlowerInfoByKeywordResponse(
        String flowerName,
        String flowerLanguage,
        List<String> flowerKeyword
) {

    public static FlowerInfoByKeywordResponse fromFlowerExpressionAndKeyword(FlowerExpression flowerExpression, List<String> flowerKeyword) {
        return new FlowerInfoByKeywordResponse(
                flowerExpression.getFlower().getFlowerName(),
                flowerExpression.getFlowerLanguage(),
                flowerKeyword
        );
    }

}
