package com.alexsandro.commander.controller;

import com.alexsandro.commander.dto.CustomerRequestDTO;
import com.alexsandro.commander.dto.CustomerResponseDTO;
import com.alexsandro.commander.exception.NotFoundException;
import com.alexsandro.commander.mapper.CustomerMapper;
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
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDTO create(@Valid @RequestBody CustomerRequestDTO dto) {
        return customerService.create(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDTO get(@PathVariable Long id) {
        return customerService.get(id).map(customerMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CustomerRequestDTO dto) {
        return customerService.update(id, dto).map(updatedCustomer ->
                ResponseEntity.ok().body(updatedCustomer)
        ).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
