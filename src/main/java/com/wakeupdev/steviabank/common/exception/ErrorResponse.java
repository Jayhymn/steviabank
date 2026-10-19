package com.wakeupdev.steviabank.common.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        Integer status,
        String error,
        String message,
        LocalDateTime timestamp,
        String path
){

}
