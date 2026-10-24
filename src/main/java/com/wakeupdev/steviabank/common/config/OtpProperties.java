package com.wakeupdev.steviabank.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "otp")
public record OtpProperties(
        int length, Duration expiry,
        int maxAttempts, Rate rate, Hash hash
) {
}

