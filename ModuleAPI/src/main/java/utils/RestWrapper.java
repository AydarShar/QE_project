package utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import models.login.Login;

import static io.restassured.RestAssured.given;

public class RestWrapper {
    public static final String URL = System.getProperty("api");
    public static RequestSpecification REQ_SPEC;
    public static final String userName = System.getProperty("userLogin");
    public static final String userPassword = System.getProperty("userPassword");

    public static String getToken() {
        String token = given()
                .body(new Login(userName, userPassword))
                .contentType("application/json")
                .baseUri(URL).when()
                .post("/login").then()
                .extract()
                .jsonPath()
                .get("access_token");
        return token;
    }

    public RequestSpecification getReqSpec() {

        REQ_SPEC = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + getToken())
                .setBaseUri(URL)
                .setContentType("application/json")
                .addFilter(new AllureRestAssured())
                .build();
        return REQ_SPEC;
    }
}
