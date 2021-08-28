import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaseholderPostTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    @Test
    public void jsonplaseholderCreateNewUser() {

        String jsonBody = "{\n" +
                "    \"name\": \"Rafał testowy\",\n" +
                "    \"username\": \"RT\",\n" +
                "    \"email\": \"Rafal@april.biz\",\n" +
                "    \"address\": {\n" +
                "      \"street\": \"Kulas Light\",\n" +
                "      \"suite\": \"Apt. 556\",\n" +
                "      \"city\": \"Gwenborough\",\n" +
                "      \"zipcode\": \"92998-3874\",\n" +
                "      \"geo\": {\n" +
                "        \"lat\": \"-37.3159\",\n" +
                "        \"lng\": \"81.1496\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"phone\": \"1-770-736-8031 x56442\",\n" +
                "    \"website\": \"hildegard.org\",\n" +
                "    \"company\": {\n" +
                "      \"name\": \"Romaguera-Crona\",\n" +
                "      \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "      \"bs\": \"harness real-time e-markets\"\n" +
                "    }\n" +
                "  }";

        Response response = given()
                .contentType("application/json") //podajemy ze typ twozony to json
                .body(jsonBody) //podajemy całe body czyli obiekt pól jsona
                .when()
                .post(BASE_URL) //metoda post
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals("Rafał testowy", json.get("name"));
        assertEquals("RT", json.get("username"));
        assertEquals("Rafal@april.biz", json.get("email"));
    }
}
