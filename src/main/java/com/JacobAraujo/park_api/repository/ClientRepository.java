package com.JacobAraujo.park_api.repository;

import com.JacobAraujo.park_api.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
