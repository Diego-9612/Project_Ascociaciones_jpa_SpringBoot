package com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.Repositories;

import java.util.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.diego.project.asociaciones.jpa.demo.project_springboot_asociaciones_jpa_demo.entities.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.students WHERE c.id =?1 ")
    Optional<Course> findOneStudents(Long id);

}
