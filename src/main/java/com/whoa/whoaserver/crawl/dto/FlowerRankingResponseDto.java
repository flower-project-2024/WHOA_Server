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
    private final String flowerRankingPrize;

    @Builder
    public FlowerRankingResponseDto(Long flowerRankingId, String flowerRankingName, String flowerRankingLanguage, String flowerRankingPrize){
        this.flowerRankingId = flowerRankingId;
        this.flowerRankingName = flowerRankingName;
        this.flowerRankingLanguage = flowerRankingLanguage;
        this.flowerRankingPrize = flowerRankingPrize;
    }
}

