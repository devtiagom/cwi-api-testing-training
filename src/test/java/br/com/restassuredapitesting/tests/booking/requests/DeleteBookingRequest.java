package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteBookingRequest {

    @Step("Deletar uma reserva com token")
    public Response deletarUmaReservaComToken(int id) {
        PostAuthRequest postAuthRequest = new PostAuthRequest();

        return given()
                .header("Content-Type", "application/json")
                .header("Cookie", postAuthRequest.getToken())
                .when()
                .delete("booking/" + id);
    }

    @Step("Deletar uma reserva sem token")
    public Response deletarUmaReservaSemToken(int id) {
        return given()
                .header("Content-Type", "application/json")
                .when()
                .delete("booking/" + id);
    }
}
