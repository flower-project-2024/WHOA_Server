package com.whoa.whoaserver.global.exception;

import lombok.Getter;

import static com.whoa.whoaserver.global.utils.LoggerUtils.logger;

@Getter
public class WhoaException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public WhoaException(final ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

	public WhoaException(ExceptionCode exceptionCode, String errorCause, String clientIP) {
		super(exceptionCode.getMessage());
		this.exceptionCode = exceptionCode;
		logger.error("CustomException occurred : requestAddress = {}, errorMethod = {}, ExceptionCode = {}, Message = {}",
			clientIP, errorCause, exceptionCode, exceptionCode.getMessage());
	}
}
