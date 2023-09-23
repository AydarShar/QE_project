package services;

import io.restassured.response.ValidatableResponse;
import models.houses.HouseRequest;
import models.houses.HouseResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class HouseService extends RestService {

    public HouseService(String token) { super(token); }

    public HouseResponse createHouse(HouseRequest rq) {
        return given().spec(REQ_SPEC).body(rq).post("/house").as(HouseResponse.class);
    }

    public List<HouseResponse> getHouses() {
        return given().spec(REQ_SPEC)
                .get("/houses")
                .jsonPath().getList("", HouseResponse.class);
    }

    public HouseResponse putHouse(HouseRequest rq, HouseResponse rs) {
        return given()
                .spec(REQ_SPEC).body(rq).put("/house/" + String.valueOf(rs.getId())).as(HouseResponse.class);
    }

    public ValidatableResponse deleteHouse(HouseResponse rs) {
        return given().spec(REQ_SPEC).delete("/house/" + String.valueOf(rs.getId()))
                .then().statusCode(204);
    }
}
