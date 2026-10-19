package com.wakeupdev.steviabank.common.sms;

public interface SmsSender {
    void send(String phoneNumber, String message);
}
