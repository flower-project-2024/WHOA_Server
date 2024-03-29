package com.whoa.whoaserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    EXIST_MEMBER(HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),
    INVALID_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),

    NOT_REGISTER_BOUQUET(HttpStatus.NOT_FOUND, "등록되지 않은 꽃다발 주문서입니다."),
    NOT_MEMBER_BOUQUET(HttpStatus.CONFLICT, "해당 유저가 주문한 꽃다발이 아닙니다."),

    IMAGE_SIZE_LIMIT_ERROR(HttpStatus.FORBIDDEN, "이미지의 크기가 기준을 초과합니다."),
    IMAGE_EXTENSION_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "지원하지 않는 이미지 파일 형식입니다.");

    private final HttpStatus status;
    private final String message;
}
