package api.customers;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CustomerCRUDTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:3100";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void CRUDShouldWork() {

        //another way of passing body
//        Map<String, Object> requestBody = new Hashtable<>();
//        requestBody.put("name", "JUnit User");
//        requestBody.put("email","junit.user@example.com");
        int customerId =
                given()
                        .contentType("application/json")
                        .body("""
                                {
                                  "name": "JUnit User",
                                  "email": "junit.user@example.com"
                                 }
                                
                                """)
                        .when()
                        .post("/customers")
                        .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .body("name", equalTo("JUnit User"))
                        .body("email", equalTo("junit.user@example.com"))
                        .extract()
                        .path("id");


        //2. fetch customer by id
        given()
                .pathParam("id", customerId)
                .when()
                .get("/customers/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(customerId))
                .body("name", equalTo("JUnit User"))
                .body("email", equalTo("junit.user@example.com"));


        //3. delete customer
        given()
                .pathParam("id", customerId)
                .when()
                .delete("/customers/{id}")
                .then()
                .statusCode(anyOf(is(200),is(204)));

        // 4. verify customer is deleted
        given()
                .pathParam("id", customerId)
                .when()
                .get("/customers/{id}")
                .then()
                .statusCode(404);

    }


}


