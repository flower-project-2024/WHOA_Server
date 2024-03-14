package com.whoa.whoaserver.global.exception;


public record ExceptionResponse(String errorType, String message) {

    public static ExceptionResponse of(String errorType, String message) {
        return new ExceptionResponse(errorType, message);
    }
}