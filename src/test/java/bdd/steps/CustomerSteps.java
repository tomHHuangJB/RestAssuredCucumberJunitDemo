package bdd.steps;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

public class CustomerSteps {
    private Response response;

    @Given("the API is running")
    public void the_api_is_running() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3100;

    }

    @When("I create a customer with name {string} and email {string}")
    public void i_create_a_customer_with_name_and_email(String name, String email) {
        String payload = """
                {
                "name": "%s",
                "email": "%s"
                }
                """.formatted(name, email);
        response =
                RestAssured
                        .given()
                        .contentType("application/json")
                        .body(payload)
                        .when()
                        .post("/customers");
    }

    @Then("the customer is created successfully")
    public void the_customer_is_created_successfully() {
        response
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("BDD User"))
                .body("email", equalTo("bdd@example.com"));
    }
}
