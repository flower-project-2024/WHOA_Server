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

    @Column(unique = true, nullable = false)
    private String fileName;

    /**
     * 다른 엔티티에서 해당 이미지를 사용할 때 true
     */
    private boolean used;

    @Builder
    private BouquetImage(String fileName, boolean used) {
        this.fileName = fileName;
        this.used = used;
    }

    public static BouquetImage create(String fileName) {
        return BouquetImage.builder()
                .fileName(fileName)
                .used(false)
                .build();
    }
}
