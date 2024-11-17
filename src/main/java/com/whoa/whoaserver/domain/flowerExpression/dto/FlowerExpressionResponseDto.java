package com.whoa.whoaserver.domain.flowerExpression.dto;

import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access=PRIVATE)
public class FlowerExpressionResponseDto {
    private final Long flowerExpressionId;
    private final String flowerColor;
    private final String flowerLanguage;
    private final String flowerImageUrl;

    public static FlowerExpressionResponseDto of(FlowerExpression flowerExpression){
        return new FlowerExpressionResponseDto(
                flowerExpression.getFlowerExpressionId(),
                flowerExpression.getFlowerColor(),
                flowerExpression.getFlowerLanguage(),
                flowerExpression.getFlowerImage().getImageUrl()
        );
    }
}
