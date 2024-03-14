package com.whoa.whoaserver.global.exception;

import lombok.Getter;

@Getter
public class WhoaException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public WhoaException(final ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
