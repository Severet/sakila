package com.example.sakila.repositories;

import com.example.sakila.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Short> {

    List<Actor> findByFullNameIgnoreCase(String name);
    List<Actor> findByFirstNameIgnoreCase(String name);
    List<Actor> findByLastNameIgnoreCase(String name);
}