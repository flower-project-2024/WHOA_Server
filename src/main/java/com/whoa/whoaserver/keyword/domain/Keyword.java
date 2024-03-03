package com.whoa.whoaserver.keyword.domain;

import com.whoa.whoaserver.flower.domain.Flower;
import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL)
    private List<Flower> flowers;

    public Keyword(
            final String keywordName
    ) {
        this.keywordName = keywordName;
    }

}

