package com.wakeupdev.steviabank.common.exception;

public record FieldViolation(
        String field,
        String message
) {
}
