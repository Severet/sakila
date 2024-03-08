package com.example.sakila.repositories;

import com.example.sakila.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Short> {

    List<Actor> findByFullNameIgnoreCase(String name);
    List<Actor> findByFirstNameIgnoreCase(String name);
    List<Actor> findByLastNameIgnoreCase(String name);
}