package com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.entities.Students;

public interface StudentRepository extends CrudRepository<Students, Long> {

    @Query("SELECT s FROM Students s LEFT JOIN FETCH s.courses WHERE s.id =?1 ")
    Optional<Students> findOneCourses(Long id);

}
