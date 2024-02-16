package com.whoa.whoaserver.flowerExpression.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whoa.whoaserver.flower.domain.Flower;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class FlowerExpression {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long flowerExpressionId;

    private String flowerColor;

    private String flowerLanguage;

    //@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)  // 다대일 단방향 관계, user 삭제되면 이벤트도 삭제
    @JoinColumn(name = "flower_id")
    private Flower flower;

    @Builder
    public FlowerExpression(
            final String flowerColor,
            final String flowerLanguage,
            final Flower flower)
    {
        this.flowerColor = flowerColor;
        this.flowerLanguage = flowerLanguage;
        this.flower = flower;
    }

}