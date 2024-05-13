package com.whoa.whoaserver.flower.dto;

import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flowerExpression.dto.FlowerExpressionResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access=PRIVATE)
public class FlowerRecommandResponseDto {
    private final Long flowerId;
    private final String flowerName;
    private final String flowerOneLineDescription;
    private final String flowerImage;
    private final List<FlowerExpressionResponseDto> flowerExpressions;

    public static FlowerRecommandResponseDto of(Flower flower) {
        List<FlowerExpressionResponseDto> expressionResponseDtos = flower.getFlowerExpressions().stream()
                .map(FlowerExpressionResponseDto::of)
                .collect(Collectors.toList());
        return new FlowerRecommandResponseDto(
                flower.getFlowerId(),
                flower.getFlowerName(),
                flower.getFlowerOneLineDescription(),
                flower.getFlowerImages().get(0),
                expressionResponseDtos
        );
    }
}
