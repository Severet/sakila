package com.example.sakila.repositories;

import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Short> {

    List<Film> findByTitleContainingIgnoreCase(String name);
}
