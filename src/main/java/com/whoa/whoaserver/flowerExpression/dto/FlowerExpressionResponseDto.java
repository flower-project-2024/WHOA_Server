package com.whoa.whoaserver.flowerExpression.dto;

import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access=PRIVATE)
public class FlowerExpressionResponseDto {
    private final String flowerColor;
    private final String flowerLanguage;

    public static FlowerExpressionResponseDto of(FlowerExpression flowerExpression){
        return new FlowerExpressionResponseDto(
                flowerExpression.getFlowerColor(),
                flowerExpression.getFlowerLanguage()
        );
    }
}
