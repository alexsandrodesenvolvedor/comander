package com.alexsandro.commander.service;

import com.alexsandro.commander.dto.CustomerResponseDTO;
import com.alexsandro.commander.dto.CustomerUpdateDTO;
import com.alexsandro.commander.model.Customer;

import java.util.Optional;

public interface CustomerService {

    Customer create(Customer customer);

    Optional<Customer> get(Long id);

    Optional<CustomerResponseDTO> update(Long id, CustomerUpdateDTO dto);

}
