Feature: Category

  Scenario: Category is fetched by id
    Given the Category with id 1 exists
    When get request is made for Category 1
    Then a Category is returned