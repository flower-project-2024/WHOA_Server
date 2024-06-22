package com.whoa.whoaserver.flower.dto;

import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flower.domain.FlowerImage;
import com.whoa.whoaserver.flowerExpression.dto.FlowerExpressionResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access=PRIVATE)
public class FlowerRecommendResponseDto {
    private final Long flowerId;
    private final String flowerName;
    private final String flowerOneLineDescription;
    private final String flowerImage;
    private final List<FlowerExpressionResponseDto> flowerExpressions;

    public static FlowerRecommendResponseDto of(Flower flower) {
        List<FlowerExpressionResponseDto> expressionResponseDtos = flower.getFlowerExpressions().stream()
                .map(FlowerExpressionResponseDto::of)
                .collect(Collectors.toList());
        String flowerImageUrl = null;
        for (FlowerImage flowerImage : flower.getFlowerImages()) {
            flowerImageUrl = flowerImage.getImageUrl();
            if (flowerImageUrl != null)
                break;
        }
        return new FlowerRecommendResponseDto(
                flower.getFlowerId(),
                flower.getFlowerName(),
                flower.getFlowerOneLineDescription(),
                flowerImageUrl,
                expressionResponseDtos
        );
    }
}
