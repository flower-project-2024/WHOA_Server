package com.whoa.whoaserver.bouquet.domain;

import com.whoa.whoaserver.bouquet.domain.type.colorTypeOption;
import com.whoa.whoaserver.bouquet.domain.type.flowerSubstitutionTypeOption;
import com.whoa.whoaserver.global.common.BaseEntity;
import com.whoa.whoaserver.member.domain.Member;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bouquet")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bouquet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bouquet_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = true)
    private String bouquetName;

    @Column(nullable = false)
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private colorTypeOption colorType;

    @Column(nullable = false)
    private String colorName;

    private String pointColor;

    @Column(nullable = false)
    private String flowerType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private flowerSubstitutionTypeOption subsitutionType;

    private String wrappingType;

    @Column(nullable = false)
    private String priceRange;

    private String requirement;

    @OneToMany(mappedBy = "bouquet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BouquetImage> images = new ArrayList<>();

    public static final String DEFAULT_WRAPPING_TYPE = "아니요, 사장님께 맡길게요";

    @Builder(access = AccessLevel.PRIVATE)
    public Bouquet(Member member, String bouquetName, String purpose, colorTypeOption colorType, String colorName, String pointColor,
                   String flowerType, flowerSubstitutionTypeOption subsitutionType, String wrappingType, String priceRange, String requirement) {
        this.member = member;
        this.bouquetName = bouquetName;
        this.purpose = purpose;
        this.colorType = colorType;
        this.colorName = colorName;
        this.pointColor = pointColor;
        this.flowerType = flowerType;
        this.subsitutionType = subsitutionType;
        this.wrappingType = wrappingType;
        this.priceRange = priceRange;
        this.requirement = requirement;
    }

    public static Bouquet orderBouquet(
        Member member,
        String bouquetName,
        String purpose,
        colorTypeOption colorType,
        String colorName,
        String pointColor,
        String flowerType,
        flowerSubstitutionTypeOption subsitutionType,
        String wrappingType,
        String priceRange,
        String requirement) {
        if (wrappingType == null) {
            wrappingType = DEFAULT_WRAPPING_TYPE;
        }
        return Bouquet.builder()
                    .member(member)
                    .bouquetName(bouquetName)
                    .purpose(purpose)
                    .colorType(colorType)
                    .colorName(colorName)
                    .pointColor(pointColor)
                    .flowerType(flowerType)
                    .subsitutionType(subsitutionType)
                    .wrappingType(wrappingType)
                    .priceRange(priceRange)
                    .requirement(requirement)
                    .build();
        
    }

    public void changeBouquet(
            String bouquetName,
            String purpose,
            colorTypeOption colorType,
            String colorName,
            String pointColor,
            String flowerType,
            flowerSubstitutionTypeOption subsitutionType,
            String wrappingType,
            String priceRange,
            String requirement) {
        this.bouquetName = bouquetName;
        this.purpose = purpose;
        this.colorType = colorType;
        this.colorName = colorName;
        this.pointColor = pointColor;
        this.flowerType = flowerType;
        this.subsitutionType = subsitutionType;
        this.wrappingType = wrappingType;
        this.priceRange = priceRange;
        this.requirement = requirement;
    };
    
    
    
}
