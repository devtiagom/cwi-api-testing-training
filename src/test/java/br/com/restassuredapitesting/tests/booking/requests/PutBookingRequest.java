package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

public class PutBookingRequest {
    PostAuthRequest postAuthRequest = new PostAuthRequest();

    @Step("Alterar uma reserva com token")
    public Response alterarUmaReservaComToken(int id, JSONObject payload) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", postAuthRequest.getToken())
                .when()
                .body(payload.toJSONString())
                .put("booking/" + id);
    }

    @Step("Alterar uma reserva sem token")
    public Response alterarUmaReservaSemToken(int id, JSONObject payload) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .body(payload.toJSONString())
                .put("booking/" + id);
    }

    @Step("Alterar uma reserva com token Inválido")
    public Response alterarUmaReservaComTokenInvalido(int id, JSONObject payload) {
        return given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=12345678")
                .when()
                .body(payload.toJSONString())
                .put("booking/" + id);
    }
}
