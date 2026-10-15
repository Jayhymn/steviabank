package com.wakeupdev.steviabank.modules.accounts;

import com.wakeupdev.steviabank.modules.customer.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public void saveAccount(Customer savedCustomer, String phoneNumber){
        String accountNumber = generateAccountNumber(phoneNumber);
        Account account = new Account();
        account.setCustomer(savedCustomer);
        account.setAccountNumber(accountNumber);
        account.setAccountType(AccountType.SAVINGS);
        account.setBalance(BigDecimal.ZERO);

        accountRepository.save(account);
    }

    private static String generateAccountNumber(String phoneNumber) {
        String rawPhone = phoneNumber.trim();
        String accountNumber;

        if (rawPhone.startsWith("0") && rawPhone.length() == 11) {
            accountNumber = rawPhone.substring(1); // Strips the first '0'
        } else if (rawPhone.startsWith("+234") && rawPhone.length() == 14) {
            accountNumber = rawPhone.substring(4); // Strips the '+234'{
        } else if (rawPhone.startsWith("+2340") && rawPhone.length() == 15) {
            accountNumber = rawPhone.substring(5);// strips +2340
        }
        else if (rawPhone.length() == 10) {
            accountNumber = rawPhone; // Already 10 digits
        } else {
            throw new RuntimeException("Invalid Nigerian phone number format. Expected 11 digits starting with 0.");
        }
        return accountNumber;
    }

}
