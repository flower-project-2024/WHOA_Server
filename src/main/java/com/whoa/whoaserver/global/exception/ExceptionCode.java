package com.whoa.whoaserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),
    FAIL_TO_GET_GOOGLE_MEMBER(1001, "구글 회원 정보를 가져오지 못했습니다."),
    EXIST_MEMBER(1002, "존재하는 회원입니다."),
    INVALID_MEMBER(1003, "존재하지 않는 회원입니다."),

    IMAGE_SIZE_LIMIT_ERROR(3000, "이미지의 크기가 기준을 초과합니다."),
    IMAGE_EXTENSION_NOT_SUPPORTED(3001, "지원하지 않는 이미지 파일 형식입니다.");

    private final int code;
    private final String message;
}
