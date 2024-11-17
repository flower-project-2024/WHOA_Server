package com.whoa.whoaserver.domain.bouquet.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ColorTypeOption {

    ONE_COLOR("단일 색"),
    TWO_COLORS("두 가지 색"),
    COLORFULL("컬러풀"),
    POINT_COLOR("포인트 컬러");

    private final String value;
}
