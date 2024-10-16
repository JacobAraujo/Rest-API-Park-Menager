package com.JacobAraujo.park_api.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientResponseDto {
    private Long id;
    private String name;
    private String cpf;
}
