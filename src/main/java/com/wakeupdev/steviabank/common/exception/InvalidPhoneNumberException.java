package com.wakeupdev.steviabank.common.exception;

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException(String message){
        super(message);
    }
}
