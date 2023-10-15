package services;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import models.cars.CarResponse;
import models.users.UserRequest;
import models.users.UserResponse;
import utils.RestWrapper;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UserService {

    public UserResponse createUser(UserRequest rq) {
        return given().body(rq).post("/user").as(UserResponse.class);
    }

    public List<UserResponse> getUsers() {
        return given().get("/users").jsonPath().getList("", UserResponse.class);
    }

    public UserResponse putUser(UserRequest rq, UserResponse rs) {
        return given().body(rq).put("/user/" + String.valueOf(rs.getId())).as(UserResponse.class);
    }

    public UserResponse postUserAndCar(UserResponse userRs, CarResponse carRs) {
        return given().post("/user/" + String.valueOf(userRs.getId()) + "/buyCar/" + String.valueOf(carRs.getId()))
                .as(UserResponse.class);
    }

    public ValidatableResponse deleteUser(UserResponse rs) {
        return given().delete("/user/" + String.valueOf(rs.getId()))
                .then().statusCode(204);
    }

    public ValidatableResponse deleteUserError(UserResponse rs) {
        return given().spec(RestAssured.requestSpecification
                        .header("", System.getProperty("userLogin"), "werw")
                        .baseUri(RestWrapper.URL)
                        .contentType("application/json")
                        .filter(new AllureRestAssured()))
                      .delete("/user/" + String.valueOf(rs.getId()))
                      .then().statusCode(403);
    }
}
