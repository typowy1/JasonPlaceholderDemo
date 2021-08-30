import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaseholderGetTwoTest {
    //inne podejscie do testow

    //Given - konfiguracja
    //When - wysyłanie requestu
    //Than - Asercja

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String USERS = "users";

    @Test
    public void jsonplaceholderReadAllUsersTest() {

        given()
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200);
    }

    @Test
    public void jsonplaceholderReadOneUserTest() {
        //można tak tez
        given()
                .when()
                .get(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("Leanne Graham")) //kożystamy z matcherów z Hromcasta
                .body("username", equalTo("Bret")) //kożystamy z matcherów z Hromcasta
                .body("email", equalTo("Sincere@april.biz")) //kożystamy z matcherów z Hromcasta
                .body("address.street", equalTo("Kulas Light"));//kożystamy z matcherów z Hromcasta
        //najlepiej łączyć dwie metody po status code dajemy extract i response i potem zapisujemy response do zmiennej i wykonujemy
        //assercje na responsie
    }

    @Test
    public void jsonplaceholderReadAllUsersNameTest() {

        Response response = given()
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response(); //nastepnie wyciagamy response do zmiennej zeby sprawdzić liste użytkowników

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");
        names.stream()
                .forEach(System.out::println);
    }

    @Test
    public void jsonplaceholderReadUsersQuantityTest() {

        Response response = given()
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response(); //nastepnie wyciagamy response do zmiennej zeby sprawdzić liste użytkowników

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");
        assertEquals(10, names.size());
    }

    //Path variables
    @Test
    public void jsonplaceholderReadOneUserWithPathVariableTest() {

        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get(BASE_URL + "/" + USERS + "/{userId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath(); //zamienimy responsa ne jsona i będziemy mieć dostęp do pól
        assertEquals("Leanne Graham", json.get("name"));
        assertEquals("Bret", json.get("username"));
        assertEquals("Sincere@april.biz", json.get("email"));
        assertEquals("Kulas Light", json.get("address.street")); //z obiektu zagnezdzonego
    }

    //Query params
    @Test
    public void jsonplaceholderReadOneUserWithQueryParamsTest() {
//tu zwróci liste uzytkownikow wiec trzeba sprawdzac z listy
        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath(); //zamienimy responsa ne jsona i będziemy mieć dostęp do pól
        // tu wyciągamy z listy
        assertEquals("Leanne Graham", json.getList("name").get(0)); // tu wyciągamy z listy
        assertEquals("Bret", json.getList("username").get(0));// tu wyciągamy z listy
        assertEquals("Sincere@april.biz", json.getList("email").get(0));// tu wyciągamy z listy
        assertEquals("Kulas Light", json.getList("address.street").get(0)); //z obiektu zagnezdzonego
    }
}
