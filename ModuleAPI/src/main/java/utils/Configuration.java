package utils;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import java.io.IOException;

public class Configuration {
    public static void setup() throws IOException {
        RestAssured.defaultParser = Parser.JSON;
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("application.properties"));
        RestAssured.requestSpecification = new RestWrapper().getReqSpec();
    }
}
