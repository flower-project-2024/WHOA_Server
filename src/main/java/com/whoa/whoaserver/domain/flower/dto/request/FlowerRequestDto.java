package com.whoa.whoaserver.domain.flower.dto.request;

import com.whoa.whoaserver.domain.flower.domain.Flower;
import com.whoa.whoaserver.domain.flower.domain.FlowerImage;
import com.whoa.whoaserver.domain.flowerExpression.domain.FlowerExpression;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class FlowerRequestDto {
    private final String flowerName;
    private final String flowerOneLineDescription;
    private final String flowerDescription;
    private final String recommendDate;
    private final String birthFlower;
    private final String comtemplationPeriod;
    private final String managementMethod;
    private final String storageMethod;
    private final List<FlowerImage> flowerImages;
    private final List<FlowerExpression> flowerExpressions;

    public Flower toEntity(List<FlowerImage> flowerImageUrls){
        return Flower.builder()
                .flowerName(flowerName)
                .flowerOneLineDescription(flowerOneLineDescription)
                .flowerDescription(flowerDescription)
                .recommendDate(recommendDate)
                .birthFlower(birthFlower)
                .birthFlower(birthFlower)
                .managementMethod(managementMethod)
                .storageMethod(storageMethod)
                .flowerImages(flowerImageUrls)
                .flowerExpressions(flowerExpressions)
                .build();
    }
}
