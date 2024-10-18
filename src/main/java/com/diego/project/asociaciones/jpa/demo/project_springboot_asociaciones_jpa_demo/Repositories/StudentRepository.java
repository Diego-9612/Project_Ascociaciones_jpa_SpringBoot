package com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.Repositories;

import org.springframework.data.repository.CrudRepository;

import com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.entities.Students;

public interface StudentRepository extends CrudRepository<Students, Long> {

}
