package com.alexsandro.commander.service;

import com.alexsandro.commander.dto.CustomerRequestDTO;
import com.alexsandro.commander.dto.CustomerResponseDTO;

import java.util.Optional;

public interface CustomerService {

    CustomerResponseDTO create(CustomerRequestDTO customer);

    CustomerResponseDTO get(Long id);

    CustomerResponseDTO update(Long id, CustomerRequestDTO dto);

    void delete(Long id);

}
