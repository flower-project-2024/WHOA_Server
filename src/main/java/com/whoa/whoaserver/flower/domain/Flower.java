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

    private String flowerOneLineDescription;

    private String flowerDescription;

    private String recommendDate;

    private String birthFlower;

    private String comtemplationPeriod;

    private String managementMethod;

    private String storageMethod;

    @ElementCollection
    private List<String> flowerImages;

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
            final String flowerOneLineDescription,
            final String birthFlower,
            final String managementMethod,
            final String storageMethod,
            final List<String> flowerImages,
            final List<FlowerExpression> flowerExpressions
    ) {
        this.flowerName = flowerName;
        this.flowerDescription = flowerDescription;
        this.flowerOneLineDescription = flowerOneLineDescription;
        this.flowerImages = flowerImages;
        this.birthFlower = birthFlower;
        this.managementMethod = managementMethod;
        this.storageMethod = storageMethod;
        this.flowerExpressions = (flowerExpressions != null) ? new ArrayList<>(flowerExpressions) : new ArrayList<>();
    }
}


