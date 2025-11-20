package com.alexsandro.commander.service;

import com.alexsandro.commander.dto.CustomerRequestDTO;
import com.alexsandro.commander.dto.CustomerResponseDTO;
import com.alexsandro.commander.model.Customer;

import java.util.Optional;

public interface CustomerService {

    CustomerResponseDTO create(CustomerRequestDTO customer);

    Optional<Customer> get(Long id);

    Optional<CustomerResponseDTO> update(Long id, CustomerRequestDTO dto);

}
