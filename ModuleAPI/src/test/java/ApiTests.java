import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pojos.CreateUserRequest;
import pojos.UserResponse;
import steps.UserSteps;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class ApiTests {

    @BeforeEach
    void defaultParser() {
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    @Tag("users")
    @DisplayName("Проверка возраста у user c фамилией Born")
    public void getUserAge() {
        given()
                .baseUri("http://77.50.236.203:4880")
                .basePath("/users")
                .contentType("application/json")
                .response()
                .when().get()
                .then()
                .statusCode(200)
                .body("find{it.secondName=='Born'}.age",
                        equalTo(30));
    }

    @Test
    @Tag("users")
    @DisplayName("Список имен users")
    public void getUsersTest() {
        List<String> firstNameList = given()
                .baseUri("http://77.50.236.203:4880")
                .basePath("/users")
                .contentType("application/json")
                .response()
                .when().get()
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("firstName");

        System.out.println(firstNameList);
    }


    @Test
    @Tag("users")
    @DisplayName("Список имен users используя steps и POJO")
    public void getUsers() {
        List<UserResponse> users = UserSteps.getUsers();

        assertThat(users).extracting(UserResponse::getFirstName).contains("Frank");
    }

    @Test
    @Tag("users")
    @DisplayName("POST / user")
    public void createUser() {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setFirstName("Ethan");
        userRequest.setSecondName("Hawke");
        userRequest.setAge(52);
        userRequest.setSex("MALE");
        userRequest.setMoney(BigDecimal.valueOf(100000.45));

        UserResponse userResponse = given()
                .baseUri("http://77.50.236.203:4880")
                .basePath("/user")
                .contentType("application/json")
                .body(userRequest)
                .when().post()
                .then().extract().as(UserResponse.class); // ObjectMapperType.JACKSON_1 ?

        assertThat(userResponse)
                .isNotNull()
                .extracting(UserResponse::getFirstName)
                .isEqualTo(userRequest.getFirstName());
    }

    @Test
    @Tag("create user")
    @DisplayName("POST user")
    public void crUser() {
//        given()
//                .body(new UserResponse( "lokesh", "admin@howtodoinjava.com", 34, "MALE", 10000.00))
//                .header(new Header("x-custom-header", "value"))
//                .contentType("application/json")
//                .when()
//                .post("/user")
//                .then()
//                .statusCode(201)
//                .body("id", notNullValue())
//                .body("name", equalTo("lokesh"))
//                .body("email", equalTo("admin@howtodoinjava.com"));

    }
}
