package com.wakeupdev.steviabank.modules.otp;

import com.wakeupdev.steviabank.common.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Setter
@Getter
@Table(name = "otp_throttle")
public class OtpThrottle extends BaseEntity {
    @Column(unique = true)
    private String phoneNumber;

    private int sendAttempt;

    private Instant nextAllowedAt;

    private Instant seriesStartedAt;
}
