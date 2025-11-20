package com.alexsandro.commander.controller;

import com.alexsandro.commander.dto.CustomerRequestDTO;
import com.alexsandro.commander.dto.CustomerResponseDTO;
import com.alexsandro.commander.mapper.CustomerMapper;
import com.alexsandro.commander.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
@Import(CustomerControllerMockMvcTest.TestConfig.class)
@SuppressWarnings("unused")
class CustomerControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @Test
    @DisplayName("criaCustomerComDadosValidosRetorna201")
    void criaCustomerComDadosValidosRetorna201() throws Exception {
        CustomerRequestDTO create = CustomerRequestDTO.builder()
                .name("Alice")
                .phone("12345678901")
                .build();

        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Alice");
        responseDTO.setPhone("12345678901");

        when(customerService.create(ArgumentMatchers.any(CustomerRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(create)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    @DisplayName("criaCustomerSemNomeRetorna400")
    void criaCustomerSemNomeRetorna400() throws Exception {
        String payload = "{ \"phone\": \"12345678901\" }";

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("criaCustomerComNomeVazioRetorna400")
    void criaCustomerComNomeVazioRetorna400() throws Exception {
        String payload = "{ \"name\": \"\", \"phone\": \"12345678901\" }";

        mockMvc.perform(post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }

    static class TestConfig {
        @Bean
        public CustomerService customerService() {
            return Mockito.mock(CustomerService.class);
        }

        @Bean
        public CustomerMapper customerMapper() {
            return Mockito.mock(CustomerMapper.class);
        }
    }
}
