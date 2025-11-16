package com.alexsandro.commander.service.impl;

import com.alexsandro.commander.dto.CustomerResponseDTO;
import com.alexsandro.commander.dto.CustomerUpdateDTO;
import com.alexsandro.commander.mapper.CustomerMapper;
import com.alexsandro.commander.model.Customer;
import com.alexsandro.commander.repository.CustomerRepository;
import com.alexsandro.commander.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public Customer create(Customer customer) {
        log.info("Salvando entidade Customer: {}", customer);
        return customerRepository.save(customer);
    }

    public Optional<Customer> get(Long id) {
        return customerRepository.findById(id).or(() -> {
            log.warn("Entidade Customer com id {} não encontrada ", id);
            return Optional.empty();
        });
    }

    public Optional<CustomerResponseDTO> update(Long id, CustomerUpdateDTO dto) {
        Optional<Customer> existingCustomer = get(id);
        return existingCustomer.map(c -> {
            //customerMapper.updateEntityFromDTO(dto, c);
            customerRepository.save(c);
            return null;//customerMapper.toDTO(c);
        });
    }

}
