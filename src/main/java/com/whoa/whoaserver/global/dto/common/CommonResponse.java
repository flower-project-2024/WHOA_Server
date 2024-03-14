package com.whoa.whoaserver.global.dto.common;

import com.whoa.whoaserver.global.exception.ExceptionResponse;

import java.time.LocalDateTime;

public record CommonResponse(LocalDateTime timestamp, boolean success, int status, Object data) {

    public static CommonResponse success(int status, Object data) {
        return new CommonResponse(LocalDateTime.now(),true, status, data);
    }

    public static CommonResponse fail(int status, ExceptionResponse exceptionResponse) {
        return new CommonResponse(LocalDateTime.now(), false, status, exceptionResponse);
    }
}
