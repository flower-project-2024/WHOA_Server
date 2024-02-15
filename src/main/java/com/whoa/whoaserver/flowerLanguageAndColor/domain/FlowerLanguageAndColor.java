package com.whoa.whoaserver.flowerLanguageAndColor.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
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
public class FlowerLanguageAndColor {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long flowerLCId;

    private String flowerColor;

    private String flowerLanguage;

    public FlowerLanguageAndColor(
            final String flowerColor,
            final String flowerLanguage)
    {
        this.flowerColor = flowerColor;
        this.flowerLanguage = flowerLanguage;
    }

}