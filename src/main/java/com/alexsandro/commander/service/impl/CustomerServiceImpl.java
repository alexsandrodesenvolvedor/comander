package com.alexsandro.commander.service.impl;

import com.alexsandro.commander.model.Customer;
import com.alexsandro.commander.repository.CustomerRepository;
import com.alexsandro.commander.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public Customer create(Customer customer) {
        log.info("Salvando entidade Customer: {}", customer);
        return customerRepository.save(customer);
    }

}
