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

    private String flowerRankingPrice;

    private String flowerRankingDate;

    private String flowerImage;

    private Long flowerId;


    @Builder(toBuilder = true)
    public FlowerRanking(
            final String flowerRankingName,
            final String flowerRankingDescription,
            final String flowerRankingPrice,
            final String flowerRankingDate,
            final String flowerImage,
            final Long flowerId
    ) {
        this.flowerRankingName = flowerRankingName;
        this.flowerRankingDescription = flowerRankingDescription;
        this.flowerRankingPrice = flowerRankingPrice;
        this.flowerRankingDate = flowerRankingDate;
        this.flowerImage = flowerImage;
        this.flowerId = flowerId;
    }

    public void updateIfPresent(String flowerRankingName, String flowerRankingDescription, String flowerRankingPrice, String flowerRankingDate, final String flowerImage,
                       final Long flowerId){
        this.flowerRankingName = flowerRankingName;
        this.flowerRankingDescription = flowerRankingDescription;
        this.flowerRankingPrice = flowerRankingPrice;
        this.flowerRankingDate = flowerRankingDate;
        this.flowerImage = flowerImage;
        this.flowerId = flowerId;
    }

    public void updateIfNotPresent(String flowerRankingName, String flowerRankingPrice, String flowerRankingDate){
        this.flowerRankingName = flowerRankingName;
        this.flowerRankingPrice = flowerRankingPrice;
        this.flowerRankingDate = flowerRankingDate;
    }
}