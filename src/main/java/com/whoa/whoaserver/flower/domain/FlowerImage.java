package com.whoa.whoaserver.flower.domain;

import com.whoa.whoaserver.flowerExpression.domain.FlowerExpression;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class FlowerImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long flowerImageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flower_id")
    private Flower flower;

    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "flower_expression_id")
    private FlowerExpression flowerExpression;

    @Builder(access = AccessLevel.PRIVATE)
    public FlowerImage(
            final Flower flower,
            final String imageUrl,
            final FlowerExpression flowerExpression
    ) {
        this.flower = flower;
        this.imageUrl = imageUrl;
        this.flowerExpression = flowerExpression;
    }

    public static FlowerImage create(
        String fileName,
        Flower flower
    ) {
        return FlowerImage.builder()
                .imageUrl(fileName)
                .flower(flower)
                .build();
    }
}
