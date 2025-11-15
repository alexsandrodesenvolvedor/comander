package com.alexsandro.commander.repository;

import com.alexsandro.commander.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
