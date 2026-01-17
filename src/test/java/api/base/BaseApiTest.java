package api.base;

import io.restassured.RestAssured;
import org.junit.Before;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;


public abstract class BaseApiTest {

    @Before
    public void setup() {

        RestAssured.baseURI = "http://localhost:3100";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected int createCustomer(String name, String email) {
        return given()
                .contentType("application/json")
                .body("""
                    {
                    "name": "%s",
                    "email": "%s"
                    }
                   """.formatted(name, email))
                .when()
                .post("/customers")
                .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    protected void deleteCustomer(int customerID) {
        given()
                .pathParam("id", customerID)
                .when()
                .delete("/customers/{id}")
                .then()
                .statusCode(anyOf(is(200), is(204)));
    }



}

