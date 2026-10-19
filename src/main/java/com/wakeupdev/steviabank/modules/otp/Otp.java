package com.wakeupdev.steviabank.modules.otp;

import com.wakeupdev.steviabank.common.persistence.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Otp extends BaseEntity {
    private String phoneNumber;
    private String code;
    private Instant expiresAt;
    private boolean used = false;
}
