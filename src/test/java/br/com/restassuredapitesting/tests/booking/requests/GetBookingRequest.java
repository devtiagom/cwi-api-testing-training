package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetBookingRequest {

    @Step("Buscar todas as reservas")
    public Response allBookings() {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .get("booking");
    }

    @Step("Buscar reservas filtrando por um parâmetro")
    public Response allBookingsByString(String param, String value) {
        return given()
                .param(param, value)
                .when()
                .get("booking");
    }
}
