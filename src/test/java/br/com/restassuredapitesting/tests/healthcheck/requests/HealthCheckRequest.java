package br.com.restassuredapitesting.tests.healthcheck.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HealthCheckRequest {

    @Step("Acessar o path padrão para verificar se a API está respondendo.")
    public Response ping() {
        return given()
                .when()
                .get("ping");
    }
}
