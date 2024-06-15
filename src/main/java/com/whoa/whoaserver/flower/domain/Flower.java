package com.whoa.whoaserver.flower.domain;

import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Flower {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long flowerId;

    private String flowerName;

    private String flowerOneLineDescription;

    private String flowerDescription;

    private String recommendDate;

    private String birthFlower;

    private String comtemplationPeriod;

    private String managementMethod;

    private String storageMethod;

    //@JsonIgnore
    @OneToMany(mappedBy = "flower",  cascade = CascadeType.ALL)
    private List<FlowerExpression> flowerExpressions = new ArrayList<>();

    @OneToMany(mappedBy = "flower", cascade = CascadeType.ALL)
    private List<FlowerImage> flowerImages = new ArrayList<>();

    @Builder(toBuilder = true)
    public Flower(
            final String flowerName,
            final String flowerDescription,
            final String flowerOneLineDescription,
            final String recommendDate,
            final String birthFlower,
            final String managementMethod,
            final String storageMethod,
            final List<FlowerExpression> flowerExpressions,
            final List<FlowerImage> flowerImages
    ) {
        this.flowerName = flowerName;
        this.flowerDescription = flowerDescription;
        this.flowerOneLineDescription = flowerOneLineDescription;
        this.recommendDate = recommendDate;
        this.birthFlower = birthFlower;
        this.managementMethod = managementMethod;
        this.storageMethod = storageMethod;
        this.flowerExpressions = (flowerExpressions != null) ? new ArrayList<>(flowerExpressions) : new ArrayList<>();
        this.flowerImages = (flowerImages != null) ? new ArrayList<>(flowerImages) : new ArrayList<>();
    }
}


