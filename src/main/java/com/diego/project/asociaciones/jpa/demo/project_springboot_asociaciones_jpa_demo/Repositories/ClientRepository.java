package com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.entities.Client;

public interface ClientRepository extends CrudRepository <Client, Long> {

    @Query("SELECT c FROM Client c JOIN FETCH c.addresses")
    Optional<Client> findOne(Long id);

}
