package api.users;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetUsersTest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:3100";
    }

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
