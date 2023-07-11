package steps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojos.UserResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UserSteps {
    public static final RequestSpecification REQUEST_SPEC =
            new RequestSpecBuilder()
                    .setBaseUri("http://77.50.236.203:4880")
                    .setBasePath("/users")
                    .setContentType("application/json")
                    .build();

    public static final List<UserResponse> getUsers() {
        return given().spec(REQUEST_SPEC)
                .get()
                .jsonPath().getList("", UserResponse.class);
    }
}
