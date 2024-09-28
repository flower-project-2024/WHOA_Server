package com.whoa.whoaserver.keyword.domain;

import com.whoa.whoaserver.mapping.domain.CustomizingPurposeKeyword;
import com.whoa.whoaserver.mapping.domain.FlowerExpressionKeyword;
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

	@OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FlowerExpressionKeyword> flowerExpressionKeywords;

	@OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CustomizingPurposeKeyword> customizingPurposeKeywords;
}
