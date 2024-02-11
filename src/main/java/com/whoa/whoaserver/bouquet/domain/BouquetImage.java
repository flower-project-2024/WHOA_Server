package com.whoa.whoaserver.bouquet.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BouquetImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String fileName;

    /**
     * 다른 엔티티에서 해당 이미지를 사용할 때 true
     */
    private boolean used;
}
