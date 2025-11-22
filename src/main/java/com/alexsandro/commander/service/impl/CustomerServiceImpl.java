package com.alexsandro.commander.service.impl;

import com.alexsandro.commander.dto.CustomerRequestDTO;
import com.alexsandro.commander.dto.CustomerResponseDTO;
import com.alexsandro.commander.exception.NotFoundException;
import com.alexsandro.commander.mapper.CustomerMapper;
import com.alexsandro.commander.model.Customer;
import com.alexsandro.commander.repository.CustomerRepository;
import com.alexsandro.commander.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerServiceImpl self;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerResponseDTO create(CustomerRequestDTO dto) {
        log.info("Salvando entidade Customer: {}", dto);
        Customer customer = customerMapper.toEntity(dto);
        customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    public CustomerResponseDTO get(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado com id: " + id));
    }

    /**
     * Método interno para obter a entidade Customer.
     *
     * @param id ID do cliente.
     * @return Optional contendo a entidade Customer, se encontrada.
     */
    public Optional<Customer> getEntity(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional
    public CustomerResponseDTO update(Long id, CustomerRequestDTO dto) {
        Optional<Customer> existingCustomer = getEntity(id);
        return existingCustomer.map(c -> {
            customerMapper.updateEntityFromDTO(dto, c);
            customerRepository.save(c);
            return customerMapper.toDTO(c);
        }).orElseThrow(() -> new NotFoundException("Cliente não encontrado com id: " + id));
    }

}
