package com.whoa.whoaserver.bouquet.domain;

import com.whoa.whoaserver.member.domain.Member;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bouquet")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bouquet {

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

    @Column(nullable = false)
    private String colorType;

    @Column(nullable = false)
    private String flowerType;

    private String wrappingType;

    @Column(nullable = false)
    private String priceRange;

    private String requirement;

    @OneToMany(mappedBy = "bouquet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BouquetImage> images = new ArrayList<>();


    @Builder
    public Bouquet(Member member, String bouquetName, String purpose, String colorType, String flowerType,
                   String wrappingType, String priceRange, String requirement) {
        this.member = member;
        this.bouquetName = bouquetName;
        this.purpose = purpose;
        this.colorType = colorType;
        this.flowerType = flowerType;
        this.wrappingType = wrappingType;
        this.priceRange = priceRange;
        this.requirement = requirement;
    }

    public static Bouquet orderBouquet(
        Member member,
        String bouquetName,
        String purpose,
        String colorType,
        String flowerType,
        String wrappingType,
        String priceRange,
        String requirement) {
        return Bouquet.builder()
                    .member(member)
                    .bouquetName(bouquetName)
                    .purpose(purpose)
                    .colorType(colorType)
                    .flowerType(flowerType)
                    .wrappingType(wrappingType)
                    .priceRange(priceRange)
                    .requirement(requirement)
                    .build();
        
    }

    public void changeBouquet(
            String bouquetName,
            String purpose,
            String colorType,
            String flowerType,
            String wrappingType,
            String priceRange,
            String requirement) {
        this.bouquetName = bouquetName;
        this.purpose = purpose;
        this.colorType = colorType;
        this.flowerType = flowerType;
        this.wrappingType = wrappingType;
        this.priceRange = priceRange;
        this.requirement = requirement;
    };
    
    
    
}
