package services;

import io.restassured.response.ValidatableResponse;
import models.cars.CarRequest;
import models.cars.CarResponse;
import models.users.UserRequest;
import models.users.UserResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UserService extends RestService {
    public UserService(String token) {
        super(token);
    }

    public UserResponse createUser(UserRequest rq) {
        return given().spec(REQ_SPEC).body(rq).post("/user").as(UserResponse.class);
    }

    public List<UserResponse> getUsers() {
        return given().spec(REQ_SPEC)
                .get("/users")
                .jsonPath().getList("", UserResponse.class);
    }

    public UserResponse putUser(UserRequest rq, UserResponse rs) {
        return given().spec(REQ_SPEC).body(rq).put("/user/" + String.valueOf(rs.getId())).as(UserResponse.class);
    }

    public ValidatableResponse deleteUser(UserResponse rs) {
        return given().spec(REQ_SPEC).delete("/user/" + String.valueOf(rs.getId()))
                .then().statusCode(204);
    }

    public ValidatableResponse deleteUserError(UserResponse rs) {
        return given().spec(REQ_SPEC).delete("/user/" + String.valueOf(rs.getId()))
                .then().statusCode(403);
    }
}
