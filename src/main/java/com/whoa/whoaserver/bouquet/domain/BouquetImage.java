package com.whoa.whoaserver.bouquet.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BouquetImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bouquet_id")
    private Bouquet bouquet;

    @Column(unique = true, nullable = false)
    private String fileName;

    @Builder
    private BouquetImage(Bouquet bouquet, String fileName) {
        this.fileName = fileName;
        this.bouquet = bouquet;
    }

    public static BouquetImage create(Bouquet bouquet, String fileName) {
        return BouquetImage.builder()
                .fileName(fileName)
                .bouquet(bouquet)
                .build();
    }

    public void update(String newFileName) {
        this.fileName = newFileName;
    }
}
