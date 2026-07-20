package com.wakeupdev.steviabank.modules.customer;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public CustomerResponse createCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.register(customerRequest);
    }

    @GetMapping("/")
    public List<CustomerResponse> findAllCustomers() {
        return customerService.findAllCustomers();
    }
}
