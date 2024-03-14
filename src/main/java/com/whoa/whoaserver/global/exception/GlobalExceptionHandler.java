package com.whoa.whoaserver.global.exception;

import com.whoa.whoaserver.global.dto.common.CommonResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpStatusCode status) {
        final String errMessage = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        final ExceptionResponse exceptionResponse = ExceptionResponse.of(e.getClass().getSimpleName(), errMessage);
        CommonResponse response = CommonResponse.fail(status.value(), exceptionResponse);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CommonResponse> handleBadRequestException(final BadRequestException e) {
        final ExceptionCode exceptionCode = e.getExceptionCode();
        final ExceptionResponse exceptionResponse = ExceptionResponse.of(exceptionCode.name(), exceptionCode.getMessage());
        final CommonResponse response = CommonResponse.fail(exceptionCode.getStatus().value(), exceptionResponse);
        return ResponseEntity.status(exceptionCode.getStatus()).body(response);
    }

}