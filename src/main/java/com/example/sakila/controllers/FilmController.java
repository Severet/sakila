package com.example.sakila.controllers;

import com.example.sakila.entities.Film;
import com.example.sakila.entities.PartialActor;
import com.example.sakila.input.FilmInput;
import com.example.sakila.input.ValidationGroup;
import com.example.sakila.repositories.ActorRepository;
import com.example.sakila.repositories.CategoryRepository;
import com.example.sakila.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@RestController
public class FilmController {
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/films")
    public List<Film> listFilms() {
        return filmRepository.findAll();
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable short id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such film"));
    }

    @PostMapping("/films")
    public Film createFilm(@Validated(ValidationGroup.Create.class) @RequestBody FilmInput data) {
        final var film = new Film();
        film.setTitle(data.getTitle());
        film.setLanguageId(data.getLanguageId());
        film.setRentalRate(data.getRentalRate());
        film.setRentalDuration(data.getRentalDuration());
        film.setReplacementCost(data.getReplacementCost());
        return filmRepository.save(film);
    }
    @PatchMapping("/films/{id}")
    public Film patchFilm(@Validated(ValidationGroup.Update.class) @RequestBody FilmInput data, @PathVariable Short id) {
        final var film = getFilmById(id);
        if (data.getTitle() != null) {
            film.setTitle(data.getTitle());
        }
        if (data.getLanguageId() != null) {
            film.setLanguageId(data.getLanguageId());
        }
        if (data.getRentalRate() != null) {
            film.setRentalRate(data.getRentalRate());
        }
        if (data.getRentalDuration() != null) {
            film.setRentalDuration(data.getRentalDuration());
        }
        if (data.getReplacementCost() != null) {
            film.setReplacementCost(data.getReplacementCost());
        }
        return filmRepository.save(film);
    }

    @DeleteMapping("/films/{id}")
    public ResponseEntity<Object> deleteFilmById(@PathVariable Short id) {
        Optional<Film> existingFilm = filmRepository.findById(id);
        if (existingFilm.isPresent()) {
            filmRepository.delete(filmRepository.getReferenceById(id));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/films/{id}/actors")
    public List<PartialActor> listCast(@PathVariable Short id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such actor")).getCast();
    }

    @PostMapping("/films/{id}/actors")
    public Film addActorToFilm(@Validated(ValidationGroup.Update.class) @RequestBody FilmInput data, @PathVariable Short id) {
        List<Short> actorIds = data.getActorIds();
        final var film = getFilmById(id);
        for (Short s: actorIds) {
            if (actorRepository.findById(s).isPresent()) {
                film.getCast()
                        .add(new PartialActor(actorRepository.getReferenceById(s)));
            }
        }
        return filmRepository.save(film);
    }

    @DeleteMapping("/films/{id}/actors")
    public ResponseEntity<Object> removeActorsFromFilm(@PathVariable Short id) {
        final var film = getFilmById(id);
        if (film != null) {
            film.setCast(null);
            filmRepository.save(film);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/films/name={name}")
    public List<Film> findFilmByTitle(@PathVariable String name) {
        if (!filmRepository.findByTitleContainingIgnoreCase(name).isEmpty()) {
            return filmRepository.findByTitleContainingIgnoreCase(name);
        }
        return null;
    }
}
