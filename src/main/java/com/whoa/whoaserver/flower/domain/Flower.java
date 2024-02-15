package com.whoa.whoaserver.flower.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.yaml.snakeyaml.events.Event;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
@Entity
@Getter
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

    private List<String> bouquetImage;

    public Flower(
            final String flowerName,
            final String flowerDescription,
            final String flowerImage,
            final List<String> bouquetImage
    ) {
        this.flowerName = flowerName;
        this.flowerDescription = flowerDescription;
        this.flowerImage = flowerImage;
        this.bouquetImage = bouquetImage;
    }
}


