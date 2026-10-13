package com.wakeupdev.steviabank.modules.customer;

public record CustomerResponse (
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
){
}
