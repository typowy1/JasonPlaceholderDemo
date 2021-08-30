package Tydzien12ZadanieDomowe;

import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdatePostTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";
    private static Faker faker;
    private static int fakeUserId;
    private static String fakeTitle;
    private static String fakeBody;

    @BeforeAll
    public static void beforeAll() {
        faker = new Faker();
    }

    @BeforeEach
    public void beforeEach() {
        fakeUserId = faker.number().numberBetween(1, 10);
        fakeTitle = faker.lorem().word();
        fakeBody = faker.lorem().sentence();
    }

    @Test
    public void updatePostPutTest() {
        JSONObject post = new JSONObject();
        post.put("userId", fakeUserId);
        post.put("title", fakeTitle);
        post.put("body", fakeBody);
        System.out.println("New post:\n" + post.toString());

        Response response = given()
                .contentType("application/json")
                .body(post.toString())
                .when()
                .put(BASE_URL + "/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Integer responseUserId = json.get("userId");

        assertEquals(fakeUserId, responseUserId);
        assertEquals(fakeTitle, json.get("title"));
        assertEquals(fakeBody, json.get("body"));
        System.out.println("Response:\n" + response.asString());
    }

    @Test
    public void updatePostPatchTest() {

        JSONObject post = new JSONObject();
        post.put("title", fakeTitle);
        System.out.println("New post:\n" + post.toString());

        Response response = given()
                .contentType("application/json")
                .body(post.toString())
                .when()
                .patch(BASE_URL + "/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals(fakeTitle, json.get("title"));
        System.out.println("Response:\n" + response.asString());
    }
}
