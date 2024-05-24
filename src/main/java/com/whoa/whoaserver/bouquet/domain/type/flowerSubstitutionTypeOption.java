package com.whoa.whoaserver.bouquet.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum flowerSubstitutionTypeOption {

    FOCUS_ON_COLOR("색감 위주"),
    FOCUS_ON_FLOWER_LANGUAGE("꽃말 위주");

    private final String value;
}
