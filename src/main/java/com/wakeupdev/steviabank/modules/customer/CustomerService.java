package com.wakeupdev.steviabank.modules.customer;


import com.wakeupdev.steviabank.modules.accounts.AccountService;
import com.wakeupdev.steviabank.modules.otp.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountService accountService;
    private final OtpService otpService;

    public CustomerResponse register(CustomerRequest customerRequest){
        if (customerRepository.existsByEmail(customerRequest.email())){
            throw new RuntimeException("Email already exists");
        }

        if (customerRepository.existsByPhoneNumber(customerRequest.phoneNumber())) {
            throw new RuntimeException("Phone number already registered");
        }

        boolean isValid = otpService.validateOtp(customerRequest.phoneNumber(), customerRequest.otp());
        if (!isValid) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        Customer customer = new Customer();
        customer.setFirstName(customerRequest.firstName());
        customer.setLastName(customerRequest.lastName());
        customer.setEmail(customerRequest.email());
        customer.setPhoneNumber(customerRequest.phoneNumber());
        customer.setPassword(customerRequest.password());

        Customer savedCustomer = customerRepository.save(customer);
        accountService.saveAccount(savedCustomer, customerRequest.phoneNumber());

        return new CustomerResponse(
                savedCustomer.getId(),
                savedCustomer.getFirstName(),
                savedCustomer.getLastName(),
                savedCustomer.getEmail(),
                savedCustomer.getPhoneNumber()
        );
    }

    public List<CustomerResponse> findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> new CustomerResponse(
                        customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail(),
                        customer.getPhoneNumber()
                ))
                .toList();
    }
}
