package com.wakeupdev.steviabank.common.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BusinessException {
    private final ErrorCode errorCode;
    private final String message;
    private final List<FieldViolation> fieldErrors;

    public BusinessException(ErrorCode errorCode, String message, List<FieldViolation> fieldErrors) {
        this.errorCode = errorCode;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }
}
