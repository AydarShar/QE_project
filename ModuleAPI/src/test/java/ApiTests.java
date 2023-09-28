import jdk.jfr.Description;
import models.cars.CarRequest;
import models.cars.CarResponse;
import models.houses.HouseRequest;
import models.houses.HouseResponse;
import org.junit.jupiter.api.*;
import models.users.UserRequest;
import models.users.UserResponse;
import services.UserService;
import utils.*;

import java.io.IOException;
import java.math.BigDecimal;


import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ApiTests {
    private static RestWrapper api;
    @BeforeAll
    public static void setup() throws IOException {
        Configuration.setup();
        api = RestWrapper.loginAs(System.getProperty("userLogin"), System.getProperty("userPassword"));
    }

    @Test
    @Tag("users")
    @DisplayName("1. GET/users - получить объекты")
    public void getUserAge() {
        step("Проверяем, что в списке есть user с фамилией Born", () ->
                assertThat(api.user.getUsers()).extracting(UserResponse::getSecondName).contains("Born"));
    }

    @Test
    @Tag("users")
    @DisplayName("2. POST/user - добавление объекта")
    public void createUser() {
        UserRequest rq = UserGenerator.getFirstUser();
        UserResponse rs = api.user.createUser(rq);
        step("Проверяем, что созданный user не null и что значение firstName в запросе и ответе совпадают", () ->
        assertThat(rs).isNotNull()
                .extracting(UserResponse::getFirstName)
                .isEqualTo(rq.getFirstName()));
    }

    @Test
    @Tag("users")
    @DisplayName("3. PUT/user/{userId} - целостное изменение объекта")
    public void putUser() {
        UserRequest request = UserGenerator.getFirstUser();
        UserResponse existingUser = api.user.createUser(request);
        UserRequest rq = UserGenerator.getSecondUser();
        UserResponse rs = api.user.putUser(rq, existingUser);
        step("Проверка, что измененная машина не null", () -> assertNotNull(rs));
        step("Сверяем значение id", () -> assertEquals(existingUser.getId(), rs.getId()));
        step("Сверяем значение mark", () -> assertEquals(rq.getSecondName(), rs.getSecondName()));
    }

    @Test
    @Tag("users")
    @DisplayName("4. DELETE/user/{id} - удаление объекта (с авторизацией)")
    public void deleteUser() {
        UserRequest rq = UserGenerator.getFirstUser();
        UserResponse rs = api.user.createUser(rq);
        step("Проверяем, что сервер вернул 204 ответ", () ->
                api.user.deleteUser(rs));
    }

    @Test
    @Tag("users")
    @DisplayName("5. DELETE/user/{id} - удаление объекта (без авторизации)")
    public void deleteUserError() {
        UserRequest rq = UserGenerator.getFirstUser();
        UserResponse rs = api.user.createUser(rq);
        UserService us = new UserService("");
        step("Проверяем, что сервер вернул 403 ответ", () ->
                us.deleteUserError(rs));
    }

    @Test
    @Tag("cars")
    @DisplayName("6. GET/cars - получить объекты")
    public void getCarsEngineType() {
        step("Проверяем, что в списке есть car с маркой Tesla", () ->
            assertThat(api.car.getCars()).extracting(CarResponse::getMark).contains("Tesla"));
    }

    @Test
    @Tag("cars")
    @DisplayName("7. POST/car - добавление объекта")
    public void createCar() {
        CarRequest rq = CarGenerator.getFirstCar();
        CarResponse rs = api.car.createCar(rq);
        step("Проверяем, что созданная машина не null и значение model в запросе и ответе совпадают", () ->
                assertThat(rs).isNotNull()
                    .extracting(CarResponse::getModel)
                    .isEqualTo(rq.getModel()));
    }

    @Test
    @Tag("cars")
    @DisplayName("8. PUT/car/{carId} - целостное изменение (перезапись) объекта")
    public void putCar() {
        CarRequest request = CarGenerator.getFirstCar();
        CarResponse existingCar = api.car.createCar(request);
        CarRequest rq = CarGenerator.getSecondCar();
        CarResponse rs = api.car.putCar(rq, existingCar);
        step("Проверка, что измененная машина не null", () -> assertNotNull(rs));
        step("Сверяем значение id", () -> assertEquals(existingCar.getId(), rs.getId()));
        step("Сверяем значение mark", () -> assertEquals(rq.getMark(), rs.getMark()));
    }

    @Test
    @Tag("cars")
    @DisplayName("9. PUT/car/{carId} - целостное изменение (перезапись) объекта")
    @Description("Проверяем, что сервер возвращает 409 ответ при наличии первичной взаимосвязи у удаляемой сущности")
    public void putCarError() {
        UserRequest userRq = UserGenerator.getFirstUser();
        UserResponse userRs = api.user.createUser(userRq);
        CarRequest carRq = CarGenerator.getFirstCar();
        CarResponse carRs = api.car.createCar(carRq);
        step("Установка первичной взаимосвязи объектов", () ->
                api.user.postUserAndCar(userRs, carRs));
        step("Проверяем, что сервер вернул 409 ответ при попытке put запроса", () ->
                api.car.putCarError(carRq, carRs));
    }

    @Test
    @Tag("houses")
    @DisplayName("10. GET /houses - получить объекты")
    public void getHouses() {
        step("Проверяем, что в списке есть house с ценой 230", () ->
                assertThat(api.house.getHouses()).extracting(HouseResponse::getPrice).contains(BigDecimal.valueOf(230.0)));
    }

    @Test
    @Tag("houses")
    @DisplayName("11. POST /house - добавление объекта")
    public void createHouse() {
        HouseRequest rq = HouseGenerator.getFirstHouse();
        HouseResponse rs = api.house.createHouse(rq);
        step("Проверяем, что созданный дом не null и что значение model в запросе и ответе совпадают", () ->
                assertThat(rs).isNotNull()
                        .extracting(HouseResponse::getPrice)
                        .isEqualTo(rq.getPrice()));
    }

    @Test
    @Tag("houses")
    @DisplayName("12. PUT /house/{houseId} - целостное изменение (перезапись) объекта")
    public void putHouse() {
        HouseRequest request = HouseGenerator.getFirstHouse();
        HouseResponse existingHouse = api.house.createHouse(request);
        HouseRequest rq = HouseGenerator.getSecondHouse();
        HouseResponse rs = api.house.putHouse(rq, existingHouse);
        step("Проверка, что измененный дом не null", () -> assertNotNull(rs));
        step("Сверяем значение id", () -> assertEquals(existingHouse.getId(), rs.getId()));
        step("Сверяем кол-во этажей", () -> assertEquals(rq.getFloorCount(), rs.getFloorCount()));
    }

    @Test
    @Tag("houses")
    @DisplayName("13. DELETE/house/{id} - удаление объекта (с авторизацией)")
    public void deleteHouse() {
        HouseRequest rq = HouseGenerator.getFirstHouse();
        HouseResponse rs = api.house.createHouse(rq);
        step("Проверяем, что сервер вернул 204 ответ", () ->
                api.house.deleteHouse(rs));
    }
}
