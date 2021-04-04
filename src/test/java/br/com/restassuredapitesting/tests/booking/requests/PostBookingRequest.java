package br.com.restassuredapitesting.tests.booking.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class PostBookingRequest {

    @Step("Criar uma nova reserva")
    public Response newBooking(JSONObject payload) {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(payload.toJSONString())
                .post("booking");
    }
}
