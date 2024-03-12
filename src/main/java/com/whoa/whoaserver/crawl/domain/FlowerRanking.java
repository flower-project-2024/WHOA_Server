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

    private String flowerRankingDescription;

    private String flowerRankingPrize;

    private String flowerRankingDate;

    @Builder(toBuilder = true)
    public FlowerRanking(
            final String flowerRankingName,
            final String flowerRankingDescription,
            final String flowerRankingPrize,
            final String flowerRankingDate
    ) {
        this.flowerRankingName = flowerRankingName;
        this.flowerRankingDescription = flowerRankingDescription;
        this.flowerRankingPrize = flowerRankingPrize;
        this.flowerRankingDate = flowerRankingDate;
    }

    public void update(String flowerRankingName, String flowerRankingDescription, String flowerRankingPrize, String flowerRankingDate){
        this.flowerRankingName = flowerRankingName;
        this.flowerRankingDescription = flowerRankingDescription;
        this.flowerRankingPrize = flowerRankingPrize;
        this.flowerRankingDate = flowerRankingDate;
    }
}