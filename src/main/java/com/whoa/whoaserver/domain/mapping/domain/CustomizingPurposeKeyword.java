package com.whoa.whoaserver.domain.mapping.domain;

import com.whoa.whoaserver.domain.customizingPurpose.domain.CustomizingPurpose;
import com.whoa.whoaserver.domain.keyword.domain.Keyword;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomizingPurposeKeyword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customizing_purpose_id")
	private CustomizingPurpose customizingPurpose;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "keyword_id")
	private Keyword keyword;
}
