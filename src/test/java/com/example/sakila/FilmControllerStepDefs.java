package com.example.sakila;

import com.example.sakila.controllers.FilmController;
import com.example.sakila.entities.Film;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class FilmControllerStepDefs {
    private FilmController mockController;
    private final Short expectedId = 1;
    private final Film expectedFilm = new Film(expectedId, "DINOSAUR ACADEMY", (byte) 2, (byte) 4, new BigDecimal("9.99"), new BigDecimal("19.99"));
    private Film actualFilm;

    @Before //cucumber before
    public void setup() {
        mockController = mock(FilmController.class);
    }

    @Given("the Film with id {short} exists")
    public void givenFilm1Exists(Short id) {
        doReturn(expectedFilm).when(mockController).getFilmById(id);
    }

    @When("get request is made for Film {short}")
    public void whenRequestWithId(Short id) {
        try {
            actualFilm = mockController.getFilmById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("a Film is returned")
    public void thenFilmReturned() {
        assertNotNull(actualFilm);
        assertEquals("DINOSAUR ACADEMY", actualFilm.getTitle());
        assertEquals(2, (byte) actualFilm.getLanguageId());
        assertEquals(4, (byte) actualFilm.getRentalDuration());
        assertEquals(new BigDecimal("9.99"), actualFilm.getRentalRate());
        assertEquals(new BigDecimal("19.99"), actualFilm.getReplacementCost());
    }
}