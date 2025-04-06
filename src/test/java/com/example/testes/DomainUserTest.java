package com.example.testes;

import com.example.domain.User;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DomainUserTest extends BaseTest{
    private User user; // Atributo compartilhado

    @BeforeEach
    public void init() {
        // Inicializando o objeto antes de cada teste
        user = new User();
    }

    @Test
    @DisplayName("POST - Registrar com sucesso")
    public void testRegisterSuccessful() {
        // Criar o objeto Usuario com campos necessários para registro
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("pistol");

        given()
                .body(user)
        .when()
                .post("/register")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", is(notNullValue()));
    }

    @Test
    @DisplayName("POST - Registro sem sucesso")
    public void testRegisterUnsuccessful() {
        // Criar o objeto Usuario com apenas o campo email
        user.setEmail("sydney@fife");

        given()
                .body(user)
        .when()
                .post("/register")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error", equalTo("Missing password"));
    }

    @Test
    @DisplayName("GET - Listar usuários")
    public void testListUsers() {
        given()
                .params("page", 2)
        .when()
                .get("/users")
        .then().
                statusCode(200).
                body("page", is(2)).
                body("data", is(notNullValue())).
                body("data.findAll { it.avatar.startsWith('https://reqres.in/img/')}.size()", is(6)); // validacao com regex de um conteudo especifco dentro de um array

    }

    @Test
    @DisplayName("GET - Listar usuário único")
    public void testSingleUser() {
        when()
                .get("/users/2")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"))
                .body("data.avatar", is("https://reqres.in/img/faces/2-image.jpg"))
                .body("support.url", is("https://contentcaddy.io?utm_source=reqres&utm_medium=json&utm_campaign=referral"))
                .body("support.text", is("Tired of writing endless social media content? Let Content Caddy generate it for you."));
    }

    @Test
    @DisplayName("GET - Usuário único não encontrado")
    public void testSingleUserNotFound() {
        when()
                .get("/users/23")
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(is("{}"));
    }

    @Test
    @DisplayName("GET - Listando resposta atrasada")
    public void testDelayedResponse() {
        given()
                .params("delay", 3)
        .when()
                .get("/users")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.email", hasItems(
                        "george.bluth@reqres.in",
                        "janet.weaver@reqres.in",
                        "emma.wong@reqres.in",
                        "eve.holt@reqres.in",
                        "charles.morris@reqres.in",
                        "tracey.ramos@reqres.in"));
    }

    @Test
    @DisplayName("DELETE - Excluir usuario")
    public void testDelete() {
        when()
                .delete("/users/2")
        .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .body(is(emptyOrNullString()));
    }

    @Test
    @DisplayName("PATCH - Atualizar usuario")
    public void testUpdatePATCH() {
        user.setName("morpheus");
        user.setJob("zion resident");

        given()
                .body(user)
        .when()
                .patch("/users/2")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    @DisplayName("POST - Criar usuario")
    public void testCreate() {
        user.setName("Fabiano");
        user.setJob("SDET");

        given()
                .body(user)
        .when()
                .post("/users")
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", is("Fabiano"))
                .body("job", is("SDET"));
    }

    @Test
    @DisplayName("PUT - Atualizar usuario")
    public void testUpdatePUT() {
        user.setName("morpheus");
        user.setJob("zion resident");

        given()
                .body(user)
        .when()
                .put("/users/2")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }
}