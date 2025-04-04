package services;

import io.restassured.response.ValidatableResponse;
import models.cars.CarRequest;
import models.cars.CarResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CarService {
    public CarResponse createCar(CarRequest rq) {
        return given().body(rq).post("/car").as(CarResponse.class);
    }

    public List<CarResponse> getCars() {
        return given().get("/cars")
                .jsonPath().getList("", CarResponse.class);
    }

    public CarResponse putCar(CarRequest rq, CarResponse rs) {
        return given().body(rq).put("/car/" + String.valueOf(rs.getId())).as(CarResponse.class);
    }

    public ValidatableResponse putCarCheckStatus(CarRequest rq, CarResponse rs, int status) {
        return given().body(rq).put("/car/" + String.valueOf(rs.getId())).then().statusCode(status);
    }
}
