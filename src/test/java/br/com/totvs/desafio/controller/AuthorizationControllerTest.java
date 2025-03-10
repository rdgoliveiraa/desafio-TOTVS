package br.com.totvs.desafio.controller;


import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import br.com.totvs.desafio.config.AbstractIntegrationTest;
import br.com.totvs.desafio.config.TestConfigs;
import br.com.totvs.desafio.infrastructure.inbound.authentication.request.UserCredentialsDTO;
import br.com.totvs.desafio.infrastructure.inbound.authentication.response.TokenDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthorizationControllerTest extends AbstractIntegrationTest {

    private static TokenDTO tokenDTO;

    @Test
    @Order(1)
    public void test_SignIn() throws Exception {
        UserCredentialsDTO user = new UserCredentialsDTO("admin", "admin123");

        tokenDTO = given()
                .basePath("auth/signin")
                    .port(TestConfigs.SERVER_PORT)
                    .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                    .when()
                .post()
                    .then()
                        .statusCode(200)
                            .extract()
                            .body()
                                .as(TokenDTO.class);

        assertNotNull(tokenDTO.accessToken());
        assertNotNull(tokenDTO.refreshToken());
    }

    @Test
    @Order(2)
    public void test_RefreshToken() throws Exception {
        UserCredentialsDTO user = new UserCredentialsDTO("admin", "admin123");

        var newtokenDTO = given()
                .basePath("auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .pathParams("username", user.username())
                    .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDTO.refreshToken())
                .when()
                    .put("{username}")
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .as(TokenDTO.class);

        assertNotNull(newtokenDTO.accessToken());
        assertNotNull(newtokenDTO.refreshToken());
    }
}
