package com.example.sakila.controllers;

import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Category;
import com.example.sakila.entities.PartialFilm;
import com.example.sakila.input.ActorInput;
import com.example.sakila.input.CategoryInput;
import com.example.sakila.input.FilmInput;
import com.example.sakila.input.ValidationGroup;
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
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    FilmRepository filmRepository;

    @GetMapping("/categories")
    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable Short id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such category"));
    }

    @PostMapping("/categories")
    public Category postCategory(@Validated(ValidationGroup.Create.class) @RequestBody CategoryInput data) {
        final var category = new Category();
        category.setCategoryName(data.getCategoryName());
        return categoryRepository.save(category);
    }

    @PatchMapping("/categories/{id}")
    public Category patchCategory(@Validated(ValidationGroup.Update.class) @RequestBody CategoryInput data, @PathVariable Short id) {
        final var category = getCategoryById(id);
        if (data.getCategoryName() != null) {
            category.setCategoryName(data.getCategoryName());
        }
        return categoryRepository.save(category);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Object> deleteCategoryById(@PathVariable Short id) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isPresent()) {
            categoryRepository.delete(categoryRepository.getReferenceById(id));
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("categories/{id}/films")
    public List<PartialFilm> listFilmsOfGenre(@PathVariable Short id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such category")).getFilms();
    }

    @PostMapping("/categories/{id}/films")
    public Category addFilmToCategory(@Validated(ValidationGroup.Update.class) @RequestBody CategoryInput data, @PathVariable Short id) {
        List<Short> filmIds = data.getFilmIds();
        final var category = getCategoryById(id);
        for (Short s : filmIds) {
            if (filmRepository.findById(s).isPresent()) {
                category.getFilms()
                        .add(new PartialFilm(filmRepository.getReferenceById(s)));
            }
        }
        return categoryRepository.save(category);
    }
        @DeleteMapping("/categories/{id}/films")
        public ResponseEntity<Object> removeFilmsFromCategory(@PathVariable Short id) {
            final var category = getCategoryById(id);
            if (category != null) {
                category.setFilms(null);
                categoryRepository.save(category);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        }

        @GetMapping("/categories/name={name}")
    public List<Category> getCategoryByName(@PathVariable String name) {
        if (!categoryRepository.findByNameContainingIgnoreCase(name).isEmpty()) {
            return categoryRepository.findByNameContainingIgnoreCase(name);
        }
        return null;
    }

}
