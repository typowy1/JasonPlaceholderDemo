package Tydzien12ZadanieDomowe;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CheckUsersTest {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    @Test
    public void readAllUsersQuantityTest() {

        Response response = given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");
        assertEquals(10, names.size());
    }

    @Test
    public void readAllUsersTest() {

        Response response = given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
        System.out.println("All users:\n" + response.asString());
    }

    @Test
    public void CheckThatEmailNotEndWithPLLoopTest() {
        Response response = given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> emails = json.getList("email");
        for (String email : emails) {
            assertFalse(email.endsWith(".pl"));
            System.out.println(email);
        }
    }

    @Test
    public void CheckThatEmailNotEndWithPLStreamTest() {
        Response response = given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> emails = json.getList("email");
        emails.stream()
                .forEach(email -> Assertions.assertFalse(email.endsWith(".pl")));
        emails.stream()
                .forEach(System.out::println);
    }
}
