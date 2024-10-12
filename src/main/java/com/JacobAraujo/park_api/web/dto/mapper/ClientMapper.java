package com.JacobAraujo.park_api.web.dto.mapper;

import com.JacobAraujo.park_api.entity.Client;
import com.JacobAraujo.park_api.web.dto.ClientCreateDto;
import com.JacobAraujo.park_api.web.dto.ClientResponseDto;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientCreateDto dto){
        return new ModelMapper().map(dto, Client.class);
    }

    public static ClientResponseDto toDto(Client client){
        return new ModelMapper().map(client, ClientResponseDto.class);
    }

}
