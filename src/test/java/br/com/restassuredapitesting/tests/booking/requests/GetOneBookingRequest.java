package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetOneBookingRequest {

    @Step("Buscar uma reserva")
    public Response oneBooking(int id) {
        return given()
                .header("Accept", "application/json")
                .when()
                .get("booking/" + id);
    }
}
