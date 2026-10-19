package com.wakeupdev.steviabank.modules.otp;


import com.wakeupdev.steviabank.common.sms.SmsSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;
    private final SmsSender smsSender;

    public void sendOtp(String phoneNumber) {
        // 1. Generate 6 digit code
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));

        // Save OTP to database
        Otp otp = new Otp();
        otp.setPhoneNumber(phoneNumber);
        otp.setCode(code);
        otp.setCreatedAt(Instant.now());
        otp.setExpiresAt(Instant.now().plus(Duration.ofMinutes(5)));

        otpRepository.save(otp);

        // Mock SMS Sending
        System.out.printf("Sending OTP %s to phone number %s%n", code, phoneNumber);
        smsSender.send(phoneNumber, "Your OTP code is: " + code);
    }

    public boolean validateOtp(String phoneNumber, String code) {
        return otpRepository.findByPhoneNumberAndCode(phoneNumber, code)
                .filter(otp -> !otp.isUsed())
                .filter(otp -> otp.getExpiresAt().isAfter(Instant.now()))
                .map(otp -> {
                    otp.setUsed(true);
                    otpRepository.save(otp);
                    return true;
                }).orElse(false);
    }
}
