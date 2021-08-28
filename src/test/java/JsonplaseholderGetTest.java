import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class JsonplaseholderGetTest {

    //Given - konfiguracja
    //When - wysyłanie requestu
    //Than - Asercja

    @Test
    public void jsonplaceholderReadAllUsers() {

        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        System.out.println(response.asString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void jsonplaceholderReadOneUser() {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users/1");
        System.out.println(response.asString());

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath(); //zamienimy responsa ne jsona i będziemy mieć dostęp do pól
        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
        Assertions.assertEquals("Kulas Light", json.get("address.street")); //z obiektu zagnezdzonego
    }

    @Test
    public void jsonplaceholderReadAllUsersName() {

        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        System.out.println(response.asString());
        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();

        List<String> names = json.getList("name");
        names.stream()
                .forEach(System.out::println);
    }

    @Test
    public void jsonplaceholderReadUsersQuantity() {

        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        System.out.println(response.asString());
        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath();

        List<String> names = json.getList("name");
        Assertions.assertEquals(10, names.size());
    }

    //Path variables
    @Test
    public void jsonplaceholderReadOneUserWithPathVariable() {

        Response response = given()
                .pathParam("userId", 1)
                .when()
                .get("https://jsonplaceholder.typicode.com/users/{userId}");

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath(); //zamienimy responsa ne jsona i będziemy mieć dostęp do pól
        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
        Assertions.assertEquals("Kulas Light", json.get("address.street")); //z obiektu zagnezdzonego
    }

    //Query params
    @Test
    public void jsonplaceholderReadOneUserWithQueryParams() {
//tu zwróci liste uzytkownikow wiec trzeba sprawdzac z listy
        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get("https://jsonplaceholder.typicode.com/users");

        Assertions.assertEquals(200, response.statusCode());

        JsonPath json = response.jsonPath(); //zamienimy responsa ne jsona i będziemy mieć dostęp do pól
        // tu wyciągamy z listy
        Assertions.assertEquals("Leanne Graham", json.getList("name").get(0)); // tu wyciągamy z listy
        Assertions.assertEquals("Bret", json.getList("username").get(0));// tu wyciągamy z listy
        Assertions.assertEquals("Sincere@april.biz", json.getList("email").get(0));// tu wyciągamy z listy
        Assertions.assertEquals("Kulas Light", json.getList("address.street").get(0)); //z obiektu zagnezdzonego
    }
}
