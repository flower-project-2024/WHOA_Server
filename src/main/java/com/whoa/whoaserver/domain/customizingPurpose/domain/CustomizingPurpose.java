package com.whoa.whoaserver.domain.customizingPurpose.domain;

import com.whoa.whoaserver.domain.mapping.domain.CustomizingPurposeKeyword;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomizingPurpose {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customizingPurposeId;

	private String purchasePurpose;

	@OneToMany(mappedBy = "customizingPurpose", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CustomizingPurposeKeyword> customizingPurposeKeywords = new ArrayList<>();
}
