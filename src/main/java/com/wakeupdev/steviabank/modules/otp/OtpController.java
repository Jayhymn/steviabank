package com.wakeupdev.steviabank.modules.otp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/otp")
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;

    public void sendOtp(String phoneNumber) {
        otpService.sendOtp(phoneNumber);
    }
}
