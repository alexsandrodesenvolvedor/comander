package com.alexsandro.commander.controller;

import com.alexsandro.commander.dto.CustomerRequestDTO;
import com.alexsandro.commander.dto.CustomerResponseDTO;
import com.alexsandro.commander.mapper.CustomerMapper;
import com.alexsandro.commander.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDTO create(@Valid @RequestBody CustomerRequestDTO dto) {
        return customerService.create(dto);
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO get(@PathVariable Long id) {
        return customerService.get(id);
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO update(@PathVariable Long id, @Valid @RequestBody CustomerRequestDTO dto) {
        return customerService.update(id, dto);
    }

}
