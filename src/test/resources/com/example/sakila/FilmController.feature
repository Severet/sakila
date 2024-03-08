Feature: Film

  Scenario: Film is fetched by id
    Given the Film with id 1 exists
    When get request is made for Film 1
    Then a Film is returned