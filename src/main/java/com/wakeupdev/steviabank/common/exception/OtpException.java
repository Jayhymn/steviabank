package com.wakeupdev.steviabank.common.exception;

import java.util.List;

public class OtpException extends BusinessException {
    public OtpException(ErrorCode errorCode, String message, List<FieldViolation> fieldErrors) {
        super(errorCode, message, fieldErrors);
    }
}
