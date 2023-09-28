package services;

import io.restassured.response.ValidatableResponse;
import models.cars.CarRequest;
import models.cars.CarResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CarService extends RestService {
    public CarService(String token) {
        super(token);
    }

    public CarResponse createCar(CarRequest rq) {
        return given().spec(REQ_SPEC).body(rq).post("/car").as(CarResponse.class);
    }

    public List<CarResponse> getCars() {
        return given().spec(REQ_SPEC)
                .get("/cars")
                .jsonPath().getList("", CarResponse.class);
    }

    public CarResponse putCar(CarRequest rq, CarResponse rs) {
        return given().spec(REQ_SPEC).body(rq).put("/car/" + String.valueOf(rs.getId())).as(CarResponse.class);
    }

    public ValidatableResponse putCarError(CarRequest rq, CarResponse rs) {
        return given().spec(REQ_SPEC).body(rq).put("/car/" + String.valueOf(rs.getId())).then().statusCode(409);
    }
}
