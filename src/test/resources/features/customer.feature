Feature: Customer management

  Scenario: Create customer successfully
    Given the API is running
    When I create a customer with name "BDD User" and email "bdd@example.com"
    Then the customer is created successfully
