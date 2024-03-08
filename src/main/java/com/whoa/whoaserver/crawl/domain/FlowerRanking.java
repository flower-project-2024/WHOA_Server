package com.whoa.whoaserver.crawl.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class FlowerRanking {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long flowerRankingId;

    private String flowerRankingName;

    private String flowerRankingLanguage;

    private String flowerRankingPrize;

    @Builder(toBuilder = true)
    public FlowerRanking(
            final String flowerRankingName,
            final String flowerRankingLanguage,
            final String flowerRankingPrize
    ) {
        this.flowerRankingName = flowerRankingName;
        this.flowerRankingLanguage = flowerRankingLanguage;
        this.flowerRankingPrize = flowerRankingPrize;
    }

    public void update(String flowerRankingName, String flowerRankingLanguage, String flowerRankingPrize){
        this.flowerRankingName = flowerRankingName;
        this.flowerRankingLanguage = flowerRankingLanguage;
        this.flowerRankingPrize = flowerRankingPrize;
    }
}