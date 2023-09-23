package utils;

import models.login.Login;
import services.CarService;
import services.HouseService;
import services.UserService;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class RestWrapper {
    private static final String URL = System.getProperty("api");
    private static String token = "";
    public UserService user;
    public CarService car;
    public HouseService house;

    public RestWrapper(String token) {
        this.token = token;

        user = new UserService(token);
        car = new CarService(token);
        house = new HouseService(token);
    }

    public static RestWrapper loginAs(String login, String password) {
        token = given()
                .body(new Login(login, password))
                .contentType("application/json")
                .baseUri(URL).when()
                .post("/login").then()
                .extract()
                .jsonPath()
                .get("access_token");

        return new RestWrapper(token);
    }
}
