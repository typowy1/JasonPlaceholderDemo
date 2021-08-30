package Tydzien12ZadanieDomowe;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeletePostTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";


    @Test
    public void jsonplaseholderDeletUserTest() {

        Response response = given()
                .contentType("application/json")
                .when()
                .delete(BASE_URL + "/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
    }
}
