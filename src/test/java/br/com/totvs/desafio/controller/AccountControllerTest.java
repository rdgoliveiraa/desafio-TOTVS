package br.com.totvs.desafio.controller;


import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.totvs.desafio.application.domain.Account;
import br.com.totvs.desafio.application.domain.Situation;
import br.com.totvs.desafio.config.AbstractIntegrationTest;
import br.com.totvs.desafio.config.TestConfigs;
import br.com.totvs.desafio.infrastructure.inbound.authentication.request.UserCredentialsDTO;
import br.com.totvs.desafio.infrastructure.inbound.authentication.response.TokenDTO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
class AccountControllerTest extends AbstractIntegrationTest {
    
    private static RequestSpecification specification;
    private static ObjectMapper mapper;
    private static Account account = new Account();

    @BeforeAll
    static void setup() {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        account = new Account();
    }

    @Test
    @Order(0)
    void authorization() throws JsonMappingException, JsonProcessingException {
        UserCredentialsDTO user = new UserCredentialsDTO("admin", "admin123");

        var accessToken = given()
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
                                .as(TokenDTO.class)
                            .accessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("/api/account/v1")
                .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    void testCreateAccount_ReturnsCreated_WhenValidData() throws Exception {
        mockAccount();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST_8080)
                    .body(mapper.writeValueAsString(account))
                    .when()
                    .post()
                .then()
                    .statusCode(201)
                        .extract()
                        .body()
                            .asString();

        Account persistedAccount = mapper.readValue(content, Account.class);
        account = persistedAccount;

        assertNotNull(persistedAccount);

        assertNotNull(persistedAccount.getId());
        assertNotNull(persistedAccount.getDescription());

        assertEquals("Conta de luz", persistedAccount.getDescription());

    }

    @Test
    @Order(2)
    void testCreateAccount_ReturnsException_WhenInvalidData() throws Exception {
        var account = new Account();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST_8080)
                .body(mapper.writeValueAsString(account))
                .when()
                .post()
                .then()
                .statusCode(500)
                .extract()
                .body()
                .asString();

        assertNotNull(content);

    }

    @Test
    @Order(3)
    void testFindByIDAccount_ReturnsCreated_WhenValidData() throws Exception {
        mockAccount();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST_8080)
                .pathParams("id", account.getId())
                    .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();

        Account persistedCategory = mapper.readValue(content, Account.class);
        account = persistedCategory;

        assertNotNull(persistedCategory);

        assertNotNull(persistedCategory.getId());
        assertNotNull(persistedCategory.getDescription());

        assertEquals("Conta de luz", persistedCategory.getDescription());

    }

    @Test
    @Order(4)
    void testFindByIDAccount_ReturnsException_WhenInvalidId() throws Exception {
        mockAccount();

        var content = given().spec(specification)
                .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST_8080)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParams("id", 100000L)
                .when()
                .get("{id}")
                .then()
                .statusCode(400)
                .extract()
                .body()
                .asString();

        assertNotNull(content);
        assertTrue(content.contains("Conta não encontrada"));

    }

    private void mockAccount() {
        account.setDueDate(Date.from(Instant.now()));
        account.setPaymentDate(Date.from(Instant.now()));
        account.setDescription("Conta de luz");
        account.setValue(500.0);
        account.updateSituation(Situation.PAID);
    }
}
