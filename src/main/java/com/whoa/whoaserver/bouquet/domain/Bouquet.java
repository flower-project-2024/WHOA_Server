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

    private Integer purpose;
    private String colorType;
    private String flowerType;
    private String wrappingType;
    private Integer  price;
    private String requirement;
    private String imagePath;

    @Builder
    public Bouquet(Member member, Integer  purpose, String colorType, String flowerType,
                   String wrappingType, Integer  price, String requirement, String imagePath) {
        this.member = member;
        this.purpose = purpose;
        this.colorType = colorType;
        this.flowerType = flowerType;
        this.wrappingType = wrappingType;
        this.price = price;
        this.requirement = requirement;
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public void setMember(Member member) {
        if (this.member == null) {
            this.member = member;
        } else if (!this.member.equals(member)) {
            throw new BadRequestException(EXIST_MEMBER);
        }
    }

    public static Bouquet orderBouquet(
        Member member,
        Integer  purpose,
        String colorType,
        String flowerType,
        String wrappingType,
        Integer  price,
        String requirement) {
        return Bouquet.builder()
                    .member(member)
                    .purpose(purpose)
                    .colorType(colorType)
                    .flowerType(flowerType)
                    .wrappingType(wrappingType)
                    .price(price)
                    .requirement(requirement)
                    .build();
        
    }

    
    
    
}
