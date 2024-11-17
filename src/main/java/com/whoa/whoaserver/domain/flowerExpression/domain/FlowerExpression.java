package com.whoa.whoaserver.domain.flowerExpression.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whoa.whoaserver.domain.flower.domain.Flower;
import com.whoa.whoaserver.domain.flower.domain.FlowerImage;
import com.whoa.whoaserver.domain.mapping.domain.FlowerExpressionKeyword;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)  // 다대일 단방향 관계
    @JoinColumn(name = "flower_id")
    private Flower flower;

    @OneToMany(mappedBy = "flowerExpression", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlowerExpressionKeyword> flowerExpressionKeywords;

    @OneToOne(mappedBy = "flowerExpression", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private FlowerImage flowerImage;

    @Builder(access = AccessLevel.PRIVATE)
    public FlowerExpression(
            final String flowerColor,
            final String flowerLanguage,
            final Flower flower,
            final FlowerImage flowerImage)
    {
        this.flowerColor = flowerColor;
        this.flowerLanguage = flowerLanguage;
        this.flower = flower;
        this.flowerImage = flowerImage;
    }

}
