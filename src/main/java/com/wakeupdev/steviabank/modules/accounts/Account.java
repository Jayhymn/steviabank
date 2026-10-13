package com.wakeupdev.steviabank.modules.accounts;


import com.wakeupdev.steviabank.common.persistence.BaseEntity;
import com.wakeupdev.steviabank.modules.customer.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "accounts")
public class Account extends BaseEntity {
    @Column(nullable = false, unique = true, length = 10)
    private String accountNumber;

    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;
}
