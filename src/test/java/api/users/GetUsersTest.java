package api.users;

import api.base.BaseApiTest;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetUsersTest extends BaseApiTest {

    @Test
    public void shouldReturnListOfUsers() {
        given()
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .body("$", not(empty()))
            .body("[0].username", equalTo("tester"));
    }
}
