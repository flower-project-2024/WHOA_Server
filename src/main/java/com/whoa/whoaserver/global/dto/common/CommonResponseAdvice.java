package com.whoa.whoaserver.global.dto.common;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.whoa.whoaserver")
public class CommonResponseAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
			@NonNull MethodParameter returnType,
			@NonNull MediaType selectedContentType,
			@NonNull Class selectedConverterType,
			@NonNull ServerHttpRequest request,
			@NonNull ServerHttpResponse response) {
        HttpServletResponse servletResponse =
                ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();
        HttpStatus resolve = HttpStatus.resolve(status);
        if (resolve == null || body instanceof String) {
            return body;
        }
        if (resolve.is2xxSuccessful()) {
            return CommonResponse.success(status, body);
        }
        return body;
    }

}
