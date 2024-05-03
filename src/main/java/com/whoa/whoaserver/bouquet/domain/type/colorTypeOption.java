package com.whoa.whoaserver.bouquet.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum colorTypeOption {

    ONE_COLOR("단일 색"),
    TWO_COLORS("두 가지 색"),
    COLORFULL("컬러풀");

    private final String value;
}
