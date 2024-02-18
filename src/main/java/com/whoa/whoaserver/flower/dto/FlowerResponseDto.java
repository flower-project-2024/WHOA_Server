package com.whoa.whoaserver.flower.dto;

import com.whoa.whoaserver.flower.domain.Flower;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.flowerExpression.dto.FlowerExpressionResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access=PRIVATE)
public class FlowerResponseDto {
    private final Long flowerId;
    private final String flowerName;
    private final String flowerDescription;
    private final String flowerImage;
    private final String birthFlower;
    private final String comtemplationPeriod;
    private final String managementMethod;
    private final String storageMethod;
    private final List<String> boquetImage;
    private final List<FlowerExpressionResponseDto> flowerExpressions;

    public static FlowerResponseDto of(Flower flower) {
        List<FlowerExpressionResponseDto> expressionResponseDtos = flower.getFlowerExpressions().stream()
                .map(FlowerExpressionResponseDto::of)
                .collect(Collectors.toList());
        return new FlowerResponseDto(
                flower.getFlowerId(),
                flower.getFlowerName(),
                flower.getFlowerDescription(),
                flower.getFlowerImage(),
                flower.getBirthFlower(),
                flower.getComtemplationPeriod(),
                flower.getManagementMethod(),
                flower.getStorageMethod(),
                flower.getBouquetImage(),
                expressionResponseDtos
        );
    }
}


