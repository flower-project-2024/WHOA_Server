package com.whoa.whoaserver.crawl.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
public class FlowerRankingResponseDto {
    private final Long flowerRankingId;
    private final String flowerRankingName;
    private final String flowerRankingLanguage;
    private final String flowerRankingPrice;
    private final String flowerRankingDate;
    private final String flowerImage;
    private final Long flowerId;

    @Builder
    public FlowerRankingResponseDto(Long flowerRankingId, String flowerRankingName, String flowerRankingLanguage, String flowerRankingPrice, String flowerRankingDate, String flowerImage, Long flowerId){
        this.flowerRankingId = flowerRankingId;
        this.flowerRankingName = flowerRankingName;
        this.flowerRankingLanguage = flowerRankingLanguage;
        this.flowerRankingPrice = flowerRankingPrice;
        this.flowerRankingDate = flowerRankingDate;
        this.flowerImage = flowerImage;
        this.flowerId = flowerId;
    }
}

