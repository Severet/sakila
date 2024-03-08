package com.example.sakila;

import com.example.sakila.controllers.ActorController;
import com.example.sakila.entities.Actor;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ActorControllerStepDefs {
    private ActorController mockController;
    private final Short expectedId = 1;
    private Actor expectedActor = new Actor(expectedId, "John", "Doe");
    private Actor actualActor;

    @Before //cucumber before
    public void setup() {
        mockController = mock(ActorController.class);
    }

    @Given("the Actor with id {short} exists")
    public void givenActor1Exists(Short id) {
        doReturn(expectedActor).when(mockController).getActorById(id);
    }

    @When("get request is made for Actor {short}")
    public void whenRequestWithId(Short id) {
        try {
            actualActor = mockController.getActorById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("an Actor is returned")
    public void thenActorReturned() {
        assertNotNull(actualActor);
        assertEquals("John", actualActor.getFirstName());
        assertEquals("Doe", actualActor.getLastName());
    }

    //Trying to use a scenario: Currently doesn't work

    @Given("An actor exists with ID {short}")
    public void anActorExistsWithID(Short id) {
        doReturn(expectedActor).when(mockController).getActorById(id);
    }
    @When("I request the details of an actor with ID {short}, first name {string} and last name {string}")
    public void iRequestThatActorDetails(Short id, String firstName, String lastName) {
        expectedActor.setFirstName(firstName);
        expectedActor.setLastName(lastName);
        actualActor = mockController.getActorById(id);
    }
    @Then("The actor's {string} and {string} are returned")
    public void theActorsNamesAreReturned(String firstName, String lastName) {
        assertNotNull(actualActor);
        assertEquals(firstName, actualActor.getFirstName());
        assertEquals(lastName, actualActor.getLastName());
    }

}
