package com.JacobAraujo.park_api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class ClientCreateDto {

    @NotBlank
    @Size(min=5, max=100)
    private String name;
    @NotBlank
    @Size(min = 11, max=11)
    @CPF
    private String cpf;
}

