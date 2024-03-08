package com.example.sakila;

import com.example.sakila.controllers.CategoryController;
import com.example.sakila.entities.Category;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class CategoryControllerStepDefs {
    private CategoryController mockController;
    private final Short expectedId = 1;
    private final Category expectedCategory = new Category(expectedId, "Horror", new ArrayList<>());
    private Category actualCategory;

    @Before //cucumber before
    public void setup() {
        mockController = mock(CategoryController.class);
    }

    @Given("the Category with id {short} exists")
    public void givenCategory1Exists(Short id) {
        doReturn(expectedCategory).when(mockController).getCategoryById(id);
    }

    @When("get request is made for Category {short}")
    public void whenRequestWithId(Short id) {
        try {
            actualCategory = mockController.getCategoryById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("a Category is returned")
    public void thenCategoryReturned() {
        assertNotNull(actualCategory);
        assertEquals("Horror", actualCategory.getCategoryName());
    }
}
