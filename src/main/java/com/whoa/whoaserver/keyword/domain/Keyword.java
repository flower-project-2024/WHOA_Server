package com.whoa.whoaserver.keyword.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Keyword {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long keywordId;

    private String keywordName;

    //Flower와 매핑 추가 필요

    public Keyword(
            final String keywordName
    ) {
        this.keywordName = keywordName;
    }

}

