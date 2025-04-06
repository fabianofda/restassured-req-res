package com.example.testes;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UnknownTest extends BaseTest{
    private static final String LIST_RESOURCE_ENDPOINT = "/unknown";
    private static final String SINGLE_RESOURCE_ENDPOINT = "/unknown/2";
    private static final String SINGLE_RESOURCE_NOT_FOUND_ENDPOINT = "/unknown/23";

    @Test
    public void testListResource() {
        when().
                get(LIST_RESOURCE_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK).
                body("data", hasSize(6)).
                body("data.name", hasItems("cerulean",
                        "fuchsia rose",
                        "true red",
                        "aqua sky",
                        "tigerlily",
                        "blue turquoise"));
    }

    @Test
    public void testSingleResource() {
        when().
                get(SINGLE_RESOURCE_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testSingleResourceNotFound() {
        when().
                get(SINGLE_RESOURCE_NOT_FOUND_ENDPOINT).
        then().
                statusCode(HttpStatus.SC_NOT_FOUND).
                body(is("{}"));
    }
}
