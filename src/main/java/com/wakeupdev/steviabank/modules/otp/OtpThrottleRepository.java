package com.wakeupdev.steviabank.modules.otp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpThrottleRepository extends JpaRepository<OtpThrottle, Long> {
    Optional<OtpThrottle> findByPhoneNumber(String phoneNumber);
}
