package com.wakeupdev.steviabank.modules.otp;


import com.wakeupdev.steviabank.common.exception.ErrorCode;
import com.wakeupdev.steviabank.common.exception.FieldViolation;
import com.wakeupdev.steviabank.common.exception.OtpException;
import com.wakeupdev.steviabank.common.sms.SmsSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
        Optional<Otp> otpCode = otpRepository.findByPhoneNumberAndCode(phoneNumber, code);
        Otp presentOtp = otpCode.orElseThrow(() -> new OtpException(ErrorCode.OTP_INVALID, List.of(
                new FieldViolation("otp", "Invalid OTP supplied for phone number")
                )
        ));

        switch (presentOtp) {
            case Otp o when o.isUsed() -> throw new OtpException(ErrorCode.OTP_ALREADY_USED, List.of(
                    new FieldViolation("otp", "This OTP has already been used")
            ));
            case Otp o when o.getExpiresAt().isBefore(Instant.now()) -> throw new OtpException(ErrorCode.OTP_EXPIRED, List.of(
                    new FieldViolation("otp", "This OTP has expired")
            ));
            default -> {
                presentOtp.setUsed(true);
                otpRepository.save(presentOtp);
                return true;
            }
        }
    }
}
