import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.put;

public class JsonplaseholderJsonObjectPutPatchTest {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    @Test
    public void jsonplaseholderUpdateUserPutTest() {

        JSONObject user = new JSONObject();
        user.put("name", "Rafał PUT testowy");
        user.put("username", "RT PUT");
        user.put("email", "RafalPUT@april.biz");
        user.put("phone", "1-770-736-8031 x56442");
        user.put("website", "hildegard.org");

        JSONObject geo = new JSONObject();
        geo.put("lat", "-37.3159");
        geo.put("lng", "-81.1496");

        JSONObject address = new JSONObject();
        address.put("street", "Kulas Light");
        address.put("suite", "Apt. 556");
        address.put("city", "Gwenborough");
        address.put("zipcode", "92998-3874");
        address.put("geo", geo);

        user.put("address", address);

        JSONObject company = new JSONObject();
        company.put("name", "Romaguera-Crona");
        company.put("catchPhrase", "Multi-layered client-server neural-net");
        company.put("bs", "harness real-time e-markets");

        user.put("company", company);
        System.out.println(user.toString());

        Response response = given()
                .contentType("application/json")
                .body(user.toString()) //tu przekazujemy stinga nie obiekt
                .when()
                .put(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Rafał PUT testowy", json.get("name"));
        Assertions.assertEquals("RT PUT", json.get("username"));
        Assertions.assertEquals("RafalPUT@april.biz", json.get("email"));
    }

    @Test
    public void jsonplaseholderUpdateUserPatchTest() {

        JSONObject userDetails = new JSONObject();

        userDetails.put("email", "RafalPATCH@april.biz");

        Response response = given()
                .contentType("application/json")
                .body(userDetails.toString())
                .when()
                .patch(BASE_URL + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("RafalPATCH@april.biz", json.get("email"));
    }

}
