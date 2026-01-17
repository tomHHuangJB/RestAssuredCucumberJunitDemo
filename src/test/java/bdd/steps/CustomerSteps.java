package bdd.steps;

import api.base.BaseApiTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
//No more restassured setup in steps
//import io.restassured.RestAssured;
//import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


/**
 * By this approach, basic RestAssured setting is in an abstract class
 * No Rest Assured setup here
 * No duplication
 * Steps are thin
 */
public class CustomerSteps extends BaseApiTest {

    private int customerId;

    @Given("the API is running")
    public void the_api_is_running() {
        // base setup already done in BaseApiTest
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = 3100;

    }

    @When("I create a customer with name {string} and email {string}")
    public void i_create_a_customer_with_name_and_email(String name, String email) {
        // old approach without BaseAPITest:
        //        String payload = """
//                {
//                "name": "%s",
//                "email": "%s"
//                }
//                """.formatted(name, email);
//        response =
//                RestAssured
//                        .given()
//                        .contentType("application/json")
//                        .body(payload)
//                        .when()
//                        .post("/customers");
        // new approach by calling method in BaseAPITest:
        customerId = createCustomer(name, email);
    }

    @Then("the customer can be retrieved")
    public void the_customer_can_be_retrieved() {
        given()
                .pathParam("id", customerId)
                .when()
                .get("/customers/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(customerId));
    }

    @Then("the customer can be deleted")
    public void the_customer_can_be_deleted() {
        deleteCustomer(customerId);
    }
}
