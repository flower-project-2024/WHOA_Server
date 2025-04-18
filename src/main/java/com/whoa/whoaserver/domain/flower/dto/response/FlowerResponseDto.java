package com.whoa.whoaserver.domain.flower.dto.response;

import com.whoa.whoaserver.domain.flower.domain.Flower;
import com.whoa.whoaserver.domain.flowerExpression.dto.FlowerExpressionResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access=PRIVATE)
public class FlowerResponseDto {
    private final Long flowerId;
    private final String flowerName;
    private final String flowerDescription;
    private final String flowerOneLineDescription;
    private final String birthFlower;
    private final String managementMethod;
    private final String storageMethod;
    private final List<FlowerExpressionResponseDto> flowerExpressions;

    public static FlowerResponseDto of(Flower flower) {
        List<FlowerExpressionResponseDto> expressionResponseDtos = flower.getFlowerExpressions().stream()
                .map(FlowerExpressionResponseDto::of)
                .collect(Collectors.toList());
        return new FlowerResponseDto(
                flower.getFlowerId(),
                flower.getFlowerName(),
                flower.getFlowerDescription(),
                flower.getFlowerOneLineDescription(),
                flower.getBirthFlower(),
                flower.getManagementMethod(),
                flower.getStorageMethod(),
                expressionResponseDtos
        );
    }
}


