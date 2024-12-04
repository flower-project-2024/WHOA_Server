package com.whoa.whoaserver.domain.flower.domain;

import com.whoa.whoaserver.global.common.BaseEntity;
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

    private String flowerRankingPrice;

    private String flowerRankingDate;

    private String flowerImage;

    private Long flowerId;


    @Builder(toBuilder = true)
    public FlowerRanking(
            final String flowerRankingName,
            final String flowerRankingLanguage,
            final String flowerRankingPrice,
            final String flowerRankingDate,
            final String flowerImage,
            final Long flowerId
    ) {
        this.flowerRankingName = flowerRankingName;
        this.flowerRankingLanguage = flowerRankingLanguage;
        this.flowerRankingPrice = flowerRankingPrice;
        this.flowerRankingDate = flowerRankingDate;
        this.flowerImage = flowerImage;
        this.flowerId = flowerId;
    }

    public void update(String flowerRankingName, String flowerRankingLanguage, String flowerRankingPrice, String flowerRankingDate, final String flowerImage,
                       final Long flowerId){
        this.flowerRankingName = flowerRankingName;
        this.flowerRankingLanguage = flowerRankingLanguage;
        this.flowerRankingPrice = flowerRankingPrice;
        this.flowerRankingDate = flowerRankingDate;
        this.flowerImage = flowerImage;
        this.flowerId = flowerId;
    }

}
