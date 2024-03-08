Feature: Actor

  Scenario: Actor is fetched by id
    Given the Actor with id 1 exists
    When get request is made for actor 1
    Then an Actor is returned