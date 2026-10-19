package com.wakeupdev.steviabank.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    OTP_INVALID(HttpStatus.BAD_REQUEST, "Invalid code"),
    OTP_EXPIRED(HttpStatus.BAD_REQUEST, "Code expired"),
    OTP_ALREADY_USED(HttpStatus.BAD_REQUEST, "Code already used"),
    OTP_TOO_MANY_ATTEMPTS(HttpStatus.TOO_MANY_REQUESTS, "Too many attempts"),
    OTP_RATE_LIMITED(HttpStatus.TOO_MANY_REQUESTS, "Please wait before requesting a new code");

    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(HttpStatus status, String defaultMessage) {
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

}