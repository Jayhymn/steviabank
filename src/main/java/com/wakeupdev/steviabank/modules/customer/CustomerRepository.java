package com.wakeupdev.steviabank.modules.customer;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Customer> findAll();

    boolean existsByPhoneNumber(String phoneNumber);
}
