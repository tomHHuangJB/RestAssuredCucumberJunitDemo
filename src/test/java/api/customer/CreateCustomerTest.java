package api.customer;


import api.base.BaseApiTest;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class CreateCustomerTest extends BaseApiTest {

    @Test
    public void shouldCreateCustomerSuccessfully() {
        Map<String, Object> customer = new HashMap<>();
        customer.put("name", "Customer-" + UUID.randomUUID());
        customer.put("email", "test@example.com");

        given()
                .contentType("application/json")
                .body(customer)
                .when()
                .post("/customers")
                .then()
                .statusCode(anyOf(is(200), is(201))) // some mock api return 200 for creation. but this one 201 correctly
                .body("id", notNullValue())
                .body("name", equalTo(customer.get("name")));

    }

    // negative test case
    @Test
    public void shouldFailWhenCustomerNameIsMissing() {

        Map<String, Object> customer = new HashMap<>();
        customer.put("email", "invalid@example.com");

        given()
                .contentType("application/json")
                .body(customer)
                .when()
                .post("/customers")
                .then()
                .statusCode(400);
    }

}
