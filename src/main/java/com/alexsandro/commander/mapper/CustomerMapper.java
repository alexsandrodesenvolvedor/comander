package com.alexsandro.commander.mapper;

import com.alexsandro.commander.dto.CustomerCreateDTO;
import com.alexsandro.commander.dto.CustomerResponseDTO;
import com.alexsandro.commander.dto.CustomerUpdateDTO;
import com.alexsandro.commander.model.Customer;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerUpdateDTO dto);
    Customer toEntity(CustomerCreateDTO dto);
    CustomerResponseDTO toDTO(Customer customer);
    void updateEntityFromDTO(CustomerUpdateDTO dto, Customer customer);

}
