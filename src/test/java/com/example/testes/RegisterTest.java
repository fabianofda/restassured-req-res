package com.example.testes;

import com.example.factories.UserBuilder;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RegisterTest extends BaseTest{
    private static final String REGISTER_ENDPOINT = "/register";

    @Test
    public void testRegisterSuccessful() {
        UserBuilder user = new UserBuilder.Builder()
                .withEmail("eve.holt@reqres.in")
                .withPassword("pistol")
                .build();

        given().
                body(user).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("token", is(notNullValue()));
    }

    @Test
    public void testRegisterUnsuccessful() {
        UserBuilder user = new UserBuilder.Builder()
                .withEmail("sydney@fife")
                .build();

        given().
                body(user).
        when().
                post(REGISTER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", equalTo("Missing password"));
    }

}