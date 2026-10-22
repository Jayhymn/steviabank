package com.wakeupdev.steviabank.common.exception;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        Integer status,
        String error,
        String message,
        Instant timestamp,
        String path,
        List<FieldViolation> fieldErrors
){

}
