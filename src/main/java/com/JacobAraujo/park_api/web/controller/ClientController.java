package com.JacobAraujo.park_api.web.controller;

import com.JacobAraujo.park_api.entity.Client;
import com.JacobAraujo.park_api.jwt.JwtUserDetails;
import com.JacobAraujo.park_api.service.ClientService;
import com.JacobAraujo.park_api.service.UserService;
import com.JacobAraujo.park_api.web.dto.ClientCreateDto;
import com.JacobAraujo.park_api.web.dto.ClientResponseDto;
import com.JacobAraujo.park_api.web.dto.UserResponseDto;
import com.JacobAraujo.park_api.web.dto.mapper.ClientMapper;
import com.JacobAraujo.park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping ("api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @Operation(summary = "Criar um novo cliente", description = "Criar um novo cliente. " +
            "Requisição exige o uso de um bearer token. Acesso restrito a Role='CLIENT'",
            responses = {
                    @ApiResponse(responseCode="201", description="Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "CPF já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil ADMIN",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> create(@RequestBody @Valid ClientCreateDto dto,
                                                    @AuthenticationPrincipal JwtUserDetails userDetails){
        Client client = ClientMapper.toClient(dto);
        client.setUser(userService.searchById(userDetails.getId()));
        clientService.save(client);
        return ResponseEntity.status(201).body(ClientMapper.toDto(client));
    }

    @Operation(summary = "Criar um novo cliente", description = "Localizar um cliente pelo id. " +
            "Requisição exige o uso de um bearer token. Acesso restrito a Role='ADMIN'",
            responses = {
                    @ApiResponse(responseCode="200", description="Recurso localizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil CLIENT",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDto> getById(@PathVariable Long id){
        Client client = clientService.searchById(id);
        return ResponseEntity.ok(ClientMapper.toDto(client));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Client>> getAll(Pageable pageable){
        Page<Client> clients = clientService.searchAll(pageable);
        return ResponseEntity.ok(clients);
    }
}