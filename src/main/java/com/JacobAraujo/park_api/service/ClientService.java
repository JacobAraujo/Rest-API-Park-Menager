package com.JacobAraujo.park_api.service;

import com.JacobAraujo.park_api.entity.Client;
import com.JacobAraujo.park_api.exception.CpfUniqueViolationException;
import com.JacobAraujo.park_api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client save(Client client){
        try{
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException ex){
            throw new CpfUniqueViolationException(String.format("CPF '%s' cannot be cadastred, already exists in system", client.getCpf()));
        }
    }
}
