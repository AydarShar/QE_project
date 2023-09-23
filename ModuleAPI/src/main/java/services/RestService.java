package services;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public abstract class RestService {
    private static final String URL = System.getProperty("api");
    protected RequestSpecification REQ_SPEC;
    protected String token = "";

    public RestService(String token) {
        this.token = token;

        REQ_SPEC = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .setBaseUri(URL)
                .setContentType("application/json")
                .addFilter(new AllureRestAssured())
                .build();
    }
}
