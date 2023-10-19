package services;

import io.restassured.response.ValidatableResponse;
import models.houses.HouseRequest;
import models.houses.HouseResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class HouseService {

    public HouseResponse createHouse(HouseRequest rq) {
        return given().body(rq).post("/house").as(HouseResponse.class);
    }

    public List<HouseResponse> getHouses() {
        return given().get("/houses")
                .jsonPath().getList("", HouseResponse.class);
    }

    public HouseResponse putHouse(HouseRequest rq, HouseResponse rs) {
        return given().body(rq).put("/house/" + String.valueOf(rs.getId())).as(HouseResponse.class);
    }

    public ValidatableResponse deleteHouse(HouseResponse rs, int status) {
        return given().delete("/house/" + String.valueOf(rs.getId()))
                .then().statusCode(status);
    }
}
