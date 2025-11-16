package com.alexsandro.commander.controller;

import com.alexsandro.commander.dto.CustomerCreateDTO;
import com.alexsandro.commander.dto.CustomerResponseDTO;
import com.alexsandro.commander.dto.CustomerUpdateDTO;
import com.alexsandro.commander.mapper.CustomerMapper;
import com.alexsandro.commander.model.Customer;
import com.alexsandro.commander.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerCreateDTO dto) {
        try {
            Customer customer = customerService.create(customerMapper.toEntity(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.toDTO(customer));
        } catch (Exception _) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> get(@PathVariable Long id) {
        return customerService.get(id)
                .map(c -> ResponseEntity.ok().body(customerMapper.toDTO(c)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CustomerUpdateDTO dto) {
        return customerService.update(id, dto).map(updatedCustomer ->
                ResponseEntity.ok().body(updatedCustomer)
        ).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
