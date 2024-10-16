package com.JacobAraujo.park_api.service;

import com.JacobAraujo.park_api.entity.Client;
import com.JacobAraujo.park_api.exception.CpfUniqueViolationException;
import com.JacobAraujo.park_api.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public Client searchById(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("id=%s id not found", id ))
        );
    }

    @Transactional(readOnly = true)
    public Page<Client> searchAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }
}
