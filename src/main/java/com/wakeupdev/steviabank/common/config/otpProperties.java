package com.wakeupdev.steviabank.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.Duration;

@EnableConfigurationProperties
public record otpProperties(
        int length, Duration expiry,
        int maxAttempts, Rate rate, Hash hash
) {
}

