package api.customers;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetCustomers {
    @Before
    public void setup() {
        RestAssured.baseURI ="http://localhost:3100";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void shouldReturnEmptyOrNonEmptyCustomerList() {
        given()
                .when()
                .get("/customers")
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .body("$", is(notNullValue()));
    }
}
