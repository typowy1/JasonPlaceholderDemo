import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JsonplaseholderBeforeAllBeforeEachTest {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
    private static Faker faker;// jest użyta w metodzie ststycznej dlatego jest statyczna
    private String fakeName;
    private String fakeUsername;
    private String fakeEmail;
    private String fakePhone;
    private String fakeWebsite;

    //https://github.com/DiUS/java-faker
//    można wygenerować dane po polsku używając konstruktora wszystko jest na stroie
//    Faker faker = new Faker(new Locale("YOUR_LOCALE"));
//    Faker faker = new Faker(new Locale("pl"));

    @BeforeAll //uruchomi się zawsze jeden raz przed wszystkimi testemi, metoda musi byc statyczna, zmienne w niej tez musza byc ztatyczne
    public static void beforeAll(){
        //przed wszystkimi testami tworzymy obiekt faker
        faker = new Faker(); // do zmiennej przypisaliśmy obiekt Faker
    }

    @BeforeEach //uruchomi się przed każdym testem
    public void beforeEach(){
        // przed każdym testem tworzymy sobie randomowe wartości
        fakeName = faker.name().fullName();
        fakeUsername = faker.name().username();
        fakeEmail = faker.internet().emailAddress(); // przed każdym testem tworzymy sobie email
        fakePhone = faker.phoneNumber().phoneNumber();
        fakeEmail = faker.internet().url();
    }

    @Test
    public void jsonplaseholderUpdateUserPutTest() {

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
