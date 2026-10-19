package com.wakeupdev.steviabank.common.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class LoggingSmsSender implements SmsSender {
    @Override
    public void send(String phoneNumber, String message) {
        log.info("Sending SMS to {}: {}", phoneNumber, message);
    }
}
