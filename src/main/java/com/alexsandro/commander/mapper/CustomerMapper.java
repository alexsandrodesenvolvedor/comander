package com.alexsandro.commander.mapper;

import com.alexsandro.commander.dto.CustomerRequestDTO;
import com.alexsandro.commander.dto.CustomerResponseDTO;
import com.alexsandro.commander.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerRequestDTO dto);
    CustomerResponseDTO toDTO(Customer customer);
    CustomerResponseDTO toDTO(CustomerRequestDTO dto);

}
