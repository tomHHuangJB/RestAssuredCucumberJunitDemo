Feature: Customer management

  Scenario: Create and delete customer successfully
    Given the API is running
    When I create a customer with name "BDD User" and email "bdd@example.com"
    Then the customer can be retrieved
    And the customer can be deleted
