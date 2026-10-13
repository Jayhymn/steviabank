package com.wakeupdev.steviabank.modules.otp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;
    private String code;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean used = false;
}
