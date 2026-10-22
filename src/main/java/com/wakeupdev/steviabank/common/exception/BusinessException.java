package com.wakeupdev.steviabank.common.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;
    private final List<FieldViolation> fieldErrors;

    public BusinessException(ErrorCode errorCode, List<FieldViolation> fieldErrors) {
        this.errorCode = errorCode;
        this.fieldErrors = fieldErrors;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.fieldErrors = null;
//        this.fieldErrors = List.of();
    }
}
