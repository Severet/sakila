package com.example.sakila.controllers;

import com.example.sakila.entities.Actor;
import com.example.sakila.entities.PartialFilm;
import com.example.sakila.input.ActorInput;
import com.example.sakila.input.ValidationGroup;
import com.example.sakila.repositories.ActorRepository;
import com.example.sakila.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@RestController
public class ActorController {
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    FilmRepository filmRepository;

    @GetMapping("/actors")
    public List<Actor> listActors() {
        return actorRepository.findAll();
    }

    @GetMapping("/actors/{id}")
    public Actor getActorById(@PathVariable Short id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such actor"));
    }

    @PostMapping("/actors")
    public Actor createActor(@Validated(ValidationGroup.Create.class) @RequestBody ActorInput data) {
        final var actor = new Actor();
        actor.setFirstName(data.getFirstName());
        actor.setLastName((data.getLastName()));
        return actorRepository.save(actor);
    }

    @PatchMapping("/actors/{id}")
    public Actor patchActor(@Validated(ValidationGroup.Update.class) @RequestBody ActorInput data, @PathVariable Short id) {
        final var actor = getActorById(id);
        if (data.getFirstName() != null) {
            actor.setFirstName(data.getFirstName());
        }
        if (data.getLastName() != null) {
            actor.setLastName(data.getLastName());
        }
        return actorRepository.save(actor);
    }

    @DeleteMapping("/actors/{id}")
    public ResponseEntity<Object> deleteActorById(@PathVariable Short id) {
        Optional<Actor> existingActor = actorRepository.findById(id);
        if (existingActor.isPresent()) {
            actorRepository.delete(actorRepository.getReferenceById(id));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/actors/{id}/films")
    public List<PartialFilm> listFilmsOfActor(@PathVariable Short id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such actor")).getFilms();
    }

    @PostMapping("/actors/{id}/films")
    public Actor addFilmToActor(@Validated(ValidationGroup.Update.class) @RequestBody ActorInput data, @PathVariable Short id) {
        List<Short> filmIds = data.getFilmIds();
        final var actor = getActorById(id);
        for (Short s: filmIds) {
            if (filmRepository.findById(s).isPresent()) {
               actor.getFilms()
                       .add(new PartialFilm(filmRepository.getReferenceById(s)));
            }
        }
        return actorRepository.save(actor);
    }

    @DeleteMapping("/actors/{id}/films")
    public ResponseEntity<Object> removeFilmsFromActor(@PathVariable Short id) {
        final var actor = getActorById(id);
        if (actor != null) {
            actor.setFilms(null);
            actorRepository.save(actor);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/actors/name={name}")
    public List<Actor> getActorByName(@PathVariable String name) {
        if (!actorRepository.findByFullNameIgnoreCase(name).isEmpty()) {
            return actorRepository.findByFullNameIgnoreCase(name);
        }
        if (!actorRepository.findByFirstNameIgnoreCase(name).isEmpty()) {
            return actorRepository.findByFirstNameIgnoreCase(name);
        }
        if (!actorRepository.findByLastNameIgnoreCase(name).isEmpty()) {
            return actorRepository.findByLastNameIgnoreCase(name);
        }
        return null;
    }
}