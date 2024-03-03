package com.whoa.whoaserver.flower.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import com.whoa.whoaserver.keyword.domain.Keyword;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.util.Lazy;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
@Entity
@Getter
@ToString
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Flower {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long flowerId;

    private String flowerName;

    private String flowerDescription;

    private String flowerImage;

    private String recommendDate;

    private String birthFlower;

    private String comtemplationPeriod;

    private String managementMethod;

    private String storageMethod;

    @ElementCollection
    private List<String> bouquetImage;

    //@JsonIgnore
    @OneToMany(mappedBy = "flower",  cascade = CascadeType.ALL)
    private List<FlowerExpression> flowerExpressions = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keywordId")
    private Keyword keyword;

    @Builder(toBuilder = true)
    public Flower(
            final String flowerName,
            final String flowerDescription,
            final String flowerImage,
            final String birthFlower,
            final String comtemplationPeriod,
            final String managementMethod,
            final String storageMethod,
            final List<FlowerExpression> flowerExpressions,
            final List<String> bouquetImage
    ) {
        this.flowerName = flowerName;
        this.flowerDescription = flowerDescription;
        this.flowerImage = flowerImage;
        this.birthFlower = birthFlower;
        this.comtemplationPeriod = comtemplationPeriod;
        this.managementMethod = managementMethod;
        this.storageMethod = storageMethod;
        this.flowerExpressions = (flowerExpressions != null) ? new ArrayList<>(flowerExpressions) : new ArrayList<>();
        this.bouquetImage = bouquetImage;
    }
}


