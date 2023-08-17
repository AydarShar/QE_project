import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import jdk.jfr.Description;
import models.cars.CarRequest;
import models.cars.CarResponse;
import models.houses.HousesResponse;
import models.login.Login;
import org.junit.jupiter.api.*;
import models.users.UserRequest;
import models.users.UserResponse;
import utils.Specifications;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class ApiTests {
private String token = "";
private static final String URL = "http://77.50.236.203:4880";

    @BeforeEach
    public void setup() {
        RestAssured.defaultParser = Parser.JSON;
        step("Спецификации для авторизации", () ->
                Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(202)));

        Login login = new Login("user@pflb.ru", "user");
        token = step("Получение токена для авторизации", () -> given()
                .body(login)
                .when()
                .post("/login")
                .then()
                .extract()
                .jsonPath()
                .get("access_token"));
    }

    @Test
    @Tag("users")
    @DisplayName("1. GET/users - получить объекты")
    @Description("Проверка возраста у user с фамилией Born")
    public void getUserAge() {
        Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(200));
        step("GET/user запрос с проверкой в теле ответа", () -> given()
                .when().get("/users")
                .then()
                .body("find{it.secondName=='Born'}.age",
                        equalTo(30)));
    }

    @Test
    @Tag("users")
    @DisplayName("2. POST/user - добавление объекта")
    @Description("Проверяем созданного пользователя")
    public String createUser() {
        Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(201));

        UserRequest createUser =
            step("Сбор user через builder", UserRequest.builder()
                    .firstName("David")
                    .secondName("Schwimmer")
                    .age(56).sex("MALE")
                    .money(BigDecimal.valueOf(130000.42))::build);

        UserResponse userResponse =
            step("POST запрос с собранным user", () -> given()
                .headers("Authorization", "Bearer " + token)
                .body(createUser)
                .when()
                .post("/user")
                .then()
                .extract()
                .as(UserResponse.class));

        System.out.println("Созданный user: " + userResponse);

        step("Проверяем, что созданный user не null и что значение firstName в запросе и ответе совпадают", () ->
        assertThat(userResponse).isNotNull()
                .extracting(UserResponse::getFirstName)
                .isEqualTo(userResponse.getFirstName()));

        return String.valueOf(userResponse.getId());
    }

    @Test
    @Tag("users")
    @DisplayName("3. DELETE/user/{id} - удаление объекта (с авторизацией, ответ 204)")
    @Description("Проверяем, что ответ 204")
    public void deleteUser() {
        Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(204));
        step("DELETE запрос с cуществующим id (id=createUser)", () ->
            given()
                .headers("Authorization", "Bearer " + token)
                .when()
                .delete("/user/" + createUser())
                .then().log().all());
    }
    @Test
    @Tag("users")
    @DisplayName("4. DELETE/user/{id} - удаление объекта (без авторизации, ответ 403)")
    @Description("Проверяем, что ответ 403")
    public void deleteUserError() {
        Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(403));
        step("DELETE запрос с несуществующим id (id=5655)", () ->
            given()
                .when()
                .delete("/user/5655")
                .then().log().all());
    }

    @Test
    @Tag("cars")
    @DisplayName("5. GET/cars - получить объекты")
    @Description("Проверяем цену самой дорогой машины")
    public void getCarsEngineType() {
        Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(200));
            List <CarResponse> cars =
                step("GET cars запрос со списком машин", () -> given()
               .when().get("/cars")
               .then()
               .extract()
               .jsonPath().getList("", CarResponse.class));

            List <BigDecimal> carsPrices =
                    step("Получение списка цен каждой машины", () ->
                            cars.stream().map(x->x.getPrice()).collect(Collectors.toList()));

        step("Проверка, что список цен не пустой", () ->
                assertNotNull(carsPrices));
        step("Поиск и сравнение цены самой дорогой машины", () ->
                assertEquals(BigDecimal.valueOf(1.4004704E+9), carsPrices.stream().max(BigDecimal::compareTo).get()));
    }

    @Test
    @Tag("cars")
    @DisplayName("6. POST/car - добавление объекта")
    @Description("Проверяем созданную машину")
    public void createCar() {
        Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(201));
            CarRequest createCarRequest = step("Сбор машины через builder", () -> CarRequest.builder()
                .engineType("Electric")
                .mark("Mercedes-Benz")
                .model("EQA")
                .price(BigDecimal.valueOf(800000))
                .build());

            CarResponse carResponse = step("POST запрос с собранной машиной", () -> given()
                .headers("Authorization", "Bearer " + token)
                .body(createCarRequest)
                .when()
                .post("/car")
                .then()
                .extract()
                .as(CarResponse.class));

        step("Проверяем, что созданная машина не null и что значение model в запросе и ответе совпадают", () ->
                assertThat(carResponse).isNotNull()
                    .extracting(CarResponse::getModel)
                    .isEqualTo(createCarRequest.getModel()));
    }

    @Test
    @Tag("cars")
    @DisplayName("7. PUT/car/{carId} - целостное изменение объекта (202 ответ)")
    @Description("Проверка измененной машины")
    public void putCar() {
        Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(202));

            CarRequest createCar = step("Сбор машины через builder", () -> CarRequest.builder()
                .engineType("Electric")
                .mark("Infinity")
                .model("Q70")
                .price(BigDecimal.valueOf(420000)).build());


            CarResponse carResponse = step("PUT запрос с собранной машиной", () -> given()
                .headers("Authorization", "Bearer " + token)
                .body(createCar)
                .when().put("car/90")
                .then()
                .extract()
                .as(CarResponse.class));

        System.out.println(carResponse);

        step("Проверка, что измененная машина не null", () -> assertNotNull(carResponse));
        step("Сверяем значение id", () -> assertEquals(90, carResponse.getId()));
        step("Сверяем значение mark", () -> assertEquals("Infinity", carResponse.getMark()));
    }

    @Test
    @Tag("cars")
    @DisplayName("8. PUT/car/{carId} - целостное изменение объекта (409 ответ)")
    @Description("Проверяем, что сервер возвращает 409 ответ при наличии первичной взаимосвязи у удаляемой сущности")
    public void putCarError() {
        Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(409));

            CarRequest createCar = step("Сбор машины через builder", () -> CarRequest.builder()
                .engineType("Electric")
                .mark("AUDI")
                .model("Q7")
                .price(BigDecimal.valueOf(480000)).build());

            step("PUT запрос с собранной машиной", () -> given()
                .headers("Authorization", "Bearer " + token)
                .body(createCar)
                .when().put("car/1")
                .then().log().all());
    }

    @Test
    @Tag("houses")
    @DisplayName("9. GET /houses - получить объекты")
    @Description("Проверка на количество жильцов в конкретном доме")
    public void getHousesWithArray() {
        Specifications.installSpec(Specifications.requestSpec(URL), Specifications.responseSpec(200));

            HousesResponse[] houses = step("GET/houses запрос с массивом из домов", () -> given()
                .headers("Authorization", "Bearer " + token)
                .when().get("/houses")
                .then()
                .extract()
                .as((Type) HousesResponse[].class));

        step("Проверка, что массив домов не пустой", () -> assertNotNull(houses));
        step("Проверка, что у 1-го дома 5 жильцов", () -> assertEquals(5, houses[0].getLodgers().size()));
    }
}
