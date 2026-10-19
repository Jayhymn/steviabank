package com.wakeupdev.steviabank.modules.accounts;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

public enum AccountStatus {
    ACTIVE,
    BLOCKED,
    CLOSED
}