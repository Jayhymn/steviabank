package com.wakeupdev.steviabank.modules.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerRequest (
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @Pattern(regexp = "^(\\+234|0)[789][01]\\d{8}$", message = "Invalid Nigerian phone number")
        @NotBlank String phoneNumber,
        @NotBlank @Size(min = 8) String password,
        @NotBlank String otp
){
}
