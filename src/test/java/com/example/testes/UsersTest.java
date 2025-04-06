package com.example.testes;

import com.example.factories.UserBuilder;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItems;

public class UsersTest extends BaseTest{
    private static final String LIST_USERS_ENDPOINT = "/users";
    private static final String SINGLE_USER_ENDPOINT = "/users/2";
    private static final String SINGLE_USER_NOT_FOUND_ENDPOINT = "/users/23";
    private static final String DELAY_RESPONSE_ENDPOINT = "/users";
    private static final String DELETE_ENDPOINT = "/users/2";
    private static final String UPDATE_ENDPOINT = "/users/2";
    private static final String CREATE_ENDPOINT = "/users";

    @Test
    public void testListUsers() {
        given().
                params("page", 2).
        when().
                get(LIST_USERS_ENDPOINT).
        then().
                statusCode(200).
                body("page", is(2),
                        "data", is(notNullValue())
                );
    }

    @Test
    public void testSingleUser() {
        when().
                get(SINGLE_USER_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("data.id", is(2),
                "data.email", is("janet.weaver@reqres.in"),
                "data.first_name", is("Janet"),
                "data.last_name", is("Weaver"),
                "data.avatar", is("https://reqres.in/img/faces/2-image.jpg"),
                "support.url", is("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral"),
                "support.text", is("Tired of writing endless social media content? Let Content Caddy generate it for you.")
                );
    }

    @Test
    public void testSingleUserNotFound() {
        when().
                get(SINGLE_USER_NOT_FOUND_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                body(is("{}"));
    }

    @Test
    public void testDelayedResponse() {
        given().
                params("delay", 3).
        when().
                get(DELAY_RESPONSE_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("data.email", hasItems("george.bluth@reqres.in",
                                "janet.weaver@reqres.in",
                                "emma.wong@reqres.in",
                                "eve.holt@reqres.in",
                                "charles.morris@reqres.in",
                                "tracey.ramos@reqres.in"));
    }

    @Test
    public void testDelete() {
        when().
                delete(DELETE_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_NO_CONTENT).
                body(is(emptyOrNullString()));
    }

    @Test
    public void testUpdatePATCH() {
        UserBuilder usuario = new UserBuilder.Builder()
                .withName("morpheus")
                .withJob("zion resident")
                .build();

        given().
                body(usuario).
        when().
                patch(UPDATE_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("name", is("morpheus"),
                "job", is("zion resident")
                );
    }

    @Test
    public void testCreate() {
        UserBuilder usuario = new UserBuilder.Builder()
                .withName("Fabiano")
                .withJob("SDET")
                .build();

        given().
                body(usuario).
        when().
                post(CREATE_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_CREATED).
                body("name", is("Fabiano"),
                "job", is("SDET")
                );
    }

    @Test
    public void testUpdatePUT() {
        UserBuilder usuario = new UserBuilder.Builder()
                .withName("morpheus")
                .withJob("zion resident")
                .build();

        given().
                body(usuario).
        when().
                put(UPDATE_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("name", is("morpheus"),
                "job", is("zion resident")
                );
    }
}