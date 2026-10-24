package com.wakeupdev.steviabank.common.config;

import java.time.Duration;

public record Rate(Duration baseDelay, Duration maxDelay, Duration resetWindow) {
}
