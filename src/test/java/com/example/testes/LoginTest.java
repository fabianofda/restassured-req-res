package com.example.testes;

import com.example.factories.UserBuilder;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginTest extends BaseTest{
    private static final String LOGIN_ENDPOINT = "/login";

    @Test
    public void testLoginSuccessful() {
        UserBuilder user = new UserBuilder.Builder()
                .withEmail("eve.holt@reqres.in")
                .withPassword("pistol")
                .build();

        given()
                .body(user)
        .when().
                post(LOGIN_ENDPOINT)
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", is(notNullValue()));
    }

    @Test
    public void testLoginUnsuccessful() {
        UserBuilder user = new UserBuilder.Builder()
                .withEmail("sydney@fife") // Apenas o email
                .build();
        given()
                .body(user)
        .when()
                .post(LOGIN_ENDPOINT)
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error", equalTo("Missing password"));
    }
}