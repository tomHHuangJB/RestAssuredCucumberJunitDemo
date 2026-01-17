package api.customers;

import api.base.BaseApiTest;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CustomerCRUDTest extends BaseApiTest {

    @Test
    public void CRUDShouldWork ()  {

        //another way of passing body
//        Map<String, Object> requestBody = new Hashtable<>();
//        requestBody.put("name", "JUnit User");
//        requestBody.put("email","junit.user@example.com");
        int customerId = createCustomer("JUnit User", "junit.user@example.com");


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
        deleteCustomer(customerId);

        // 4. verify customer is deleted
        given()
                .pathParam("id", customerId)
                .when()
                .get("/customers/{id}")
                .then()
                .statusCode(404);

    }

    @Test
    public void shouldReturn404WhenCustomerDoesNotExist() {

        given()
                .pathParam("id", 9999)
                .when()
                .get("/customers/{id}")
                .then()
                .statusCode(404);
    }


}

