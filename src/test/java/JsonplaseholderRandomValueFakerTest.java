import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JsonplaseholderRandomValueFakerTest {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/users";

    //https://github.com/DiUS/java-faker
//    można wygenerować dane po polsku używając konstruktora wszystko jest na stroie
//    Faker faker = new Faker(new Locale("YOUR_LOCALE"));
//    Faker faker = new Faker(new Locale("pl"));

    @Test
    public void jsonplaseholderUpdateUserPutTest() {

        Faker faker = new Faker();
        String fakeName = faker.name().fullName();
        String fakeUsername = faker.name().username();
        String fakeEmail = faker.internet().emailAddress();
        String fakePhone = faker.phoneNumber().phoneNumber();
        String fakeWebsite = faker.internet().url();

        JSONObject user = new JSONObject();
        user.put("name", fakeName);
        user.put("username", fakeUsername);
        user.put("email", fakeEmail);
        user.put("phone", fakePhone);
        user.put("website", fakeWebsite);

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
        Assertions.assertEquals(fakeName, json.get("name"));
        Assertions.assertEquals(fakeUsername, json.get("username"));
        Assertions.assertEquals(fakeEmail, json.get("email"));
    }

    @Test
    public void jsonplaseholderUpdateUserPatchTest() {

        Faker faker = new Faker();
        String fakeEmail = faker.internet().emailAddress();

        JSONObject userDetails = new JSONObject();

        userDetails.put("email", fakeEmail);

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
        Assertions.assertEquals(fakeEmail, json.get("email"));
    }

}
