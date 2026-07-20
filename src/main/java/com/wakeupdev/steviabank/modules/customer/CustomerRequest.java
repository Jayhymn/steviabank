package com.wakeupdev.steviabank.modules.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRequest (
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotBlank String phoneNumber,
        @NotBlank @Size(min = 8) String password
){
}
