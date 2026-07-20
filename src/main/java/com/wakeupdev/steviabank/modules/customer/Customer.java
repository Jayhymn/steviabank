package com.wakeupdev.steviabank.modules.customer;

import com.wakeupdev.steviabank.modules.accounts.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    private String email;

    // A customer can have multiple accounts (Savings, Checking, etc.)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Account> accounts;
}
