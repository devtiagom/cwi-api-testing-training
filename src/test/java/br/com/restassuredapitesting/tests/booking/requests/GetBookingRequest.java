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
    public Response allBookingsByOneParam(String param, String value) {
        return given()
                .param(param, value)
                .when()
                .get("booking");
    }

    @Step("Buscar reservas filtrando por datas de checkin e checkout")
    public Response allBookingsByCheckinAndCheckout(String checkin, String checkout) {
        return given()
                .param("checkin", checkin)
                .param("checkout", checkout)
                .when()
                .get("booking");
    }

    @Step("Buscar reservas filtrando por nome e datas de checkin e checkout")
    public Response allBookingsByNameAndCheckinAndCheckout(String firstname, String checkin, String checkout) {
        return given()
                .param("firstname", firstname)
                .param("checkin", checkin)
                .param("checkout", checkout)
                .when()
                .get("booking");
    }
}
