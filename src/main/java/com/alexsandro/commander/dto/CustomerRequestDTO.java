package com.alexsandro.commander.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerRequestDTO {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 11)
    private String phone;

}
