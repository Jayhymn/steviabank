package com.wakeupdev.steviabank.modules.otp;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;

    public void sendOtp(String phoneNumber) {
        // 1. Generate 6 digit code
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));

        // Save OTP to database
        Otp otp = new Otp();
        otp.setPhoneNumber(phoneNumber);
        otp.setCode(code);
        otp.setCreatedAt(LocalDateTime.now());
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(otp);

        // Mock SMS Sending
        System.out.printf("Sending OTP %s to phone number %s%n", code, phoneNumber);
    }

    public boolean validateOtp(String phoneNumber, String code) {
        return otpRepository.findByPhoneNumberAndCode(phoneNumber, code)
                .filter(otp -> !otp.isUsed())
                .filter(otp -> otp.getExpiresAt().isAfter(LocalDateTime.now()))
                .map(otp -> {
                    otp.setUsed(true);
                    otpRepository.save(otp);
                    return true;
                }).orElse(false);
    }
}
