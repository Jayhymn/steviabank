package com.wakeupdev.steviabank.modules.customer;


import com.wakeupdev.steviabank.modules.accounts.Account;
import com.wakeupdev.steviabank.modules.accounts.AccountRepository;
import com.wakeupdev.steviabank.modules.accounts.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public CustomerResponse register(CustomerRequest customerRequest){
        if (customerRepository.existsByEmail(customerRequest.email())){
            throw new RuntimeException("Email already exists");
        }

        if (customerRepository.existsByPhoneNumber(customerRequest.phoneNumber())) {
            throw new RuntimeException("Phone number already registered");
        }

        String rawPhone = customerRequest.phoneNumber().trim();
        String accountNumber;

        if (rawPhone.startsWith("0") && rawPhone.length() == 11) {
            accountNumber = rawPhone.substring(1); // Strips the first '0'
        } else if (rawPhone.startsWith("+234") && rawPhone.length() == 14) {
            accountNumber = rawPhone.substring(4); // Strips the '+234'{
        } else if (rawPhone.length() == 10) {
            accountNumber = rawPhone; // Already 10 digits
        } else {
            throw new RuntimeException("Invalid Nigerian phone number format. Expected 11 digits starting with 0.");
        }

        Customer customer = new Customer();
        customer.setFirstName(customerRequest.firstName());
        customer.setLastName(customerRequest.lastName());
        customer.setEmail(customerRequest.email());
        customer.setPhoneNumber(customerRequest.phoneNumber());
        customer.setPassword(customerRequest.password());

        Customer savedCustomer = customerRepository.save(customer);

        Account account = new Account();
        account.setCustomer(savedCustomer);
        account.setAccountNumber(accountNumber);
        account.setAccountType(AccountType.SAVINGS);
        account.setBalance(BigDecimal.ZERO);

        accountRepository.save(account);

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
