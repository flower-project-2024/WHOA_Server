package com.whoa.whoaserver.bouquet.domain;

import static com.whoa.whoaserver.global.exception.ExceptionCode.EXIST_MEMBER;

import com.whoa.whoaserver.global.exception.BadRequestException;
import com.whoa.whoaserver.member.domain.Member;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String imagePath;

    @Builder
    public Bouquet(Member member, String purpose, String colorType, String flowerType,
                   String wrappingType, String priceRange, String requirement, String imagePath) {
        this.member = member;
        this.purpose = purpose;
        this.colorType = colorType;
        this.flowerType = flowerType;
        this.wrappingType = wrappingType;
        this.priceRange = priceRange;
        this.requirement = requirement;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public static Bouquet orderBouquet(
        Member member,
        String purpose,
        String colorType,
        String flowerType,
        String wrappingType,
        String priceRange,
        String requirement) {
        return Bouquet.builder()
                    .member(member)
                    .purpose(purpose)
                    .colorType(colorType)
                    .flowerType(flowerType)
                    .wrappingType(wrappingType)
                    .priceRange(priceRange)
                    .requirement(requirement)
                    .build();
        
    }

    
    
    
}
