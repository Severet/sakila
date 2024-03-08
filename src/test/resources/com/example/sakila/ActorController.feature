Feature: Actor

  Scenario: Actor is fetched by id
    Given the Actor with id 1 exists
    When get request is made for Actor 1
    Then an Actor is returned

  Scenario Outline: get a specific actor
    Given An actor exists with ID <actorID>
    When I request the details of an actor with ID <actorID>, first name "<firstName>" and last name "<lastName>"
    Then The actor's "<firstName>" and "<lastName>" are returned
    Examples:
      | actorID | firstName | lastName     |
      | 1       | PENELOPE  | GUINESS      |
      | 5       | JOHNNY    | LOLLOBRIGIDA |
      | 13      | UMA       | WOOD         |
      | 53      | MENA      | TEMPLE       |
      | 79      | MAE       | HOFFMAN      |