package Tydzien12ZadanieDomowe;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadPostTest {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/posts";

    @Test
    public void readPostsQuantityTest() {
        Response response = given()
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> ids = json.getList("id");
        assertEquals(100, ids.size());
    }

    @Test
    public void readOnePostWithPathVariableTest() {
        Response response = given()
                .pathParam("postId", 1)
                .when()
                .get(BASE_URL + "/{postId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Integer responseUserId = json.get("userId");

        assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", json.get("title"));
        assertEquals(1, responseUserId);
    }

    @Test
    public void readOnePostWithQueryParamsTest() {
        Response response = given()
                .queryParam("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit")
                .when()
                .get(BASE_URL)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(1, json.getList("userId").get(0));
        assertEquals(1, json.getList("id").get(0));
        assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                json.getList("title").get(0));
    }
}
