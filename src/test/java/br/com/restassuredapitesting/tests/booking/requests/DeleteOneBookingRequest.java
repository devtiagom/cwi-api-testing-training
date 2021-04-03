package br.com.restassuredapitesting.tests.booking.requests;

import br.com.restassuredapitesting.tests.auth.requests.PostAuthRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DeleteOneBookingRequest {

    @Step("Deletar uma reserva com token")
    public Response deletarUmaReservaComToken(int id) {
        PostAuthRequest postAuthRequest = new PostAuthRequest();

        return given()
                .header("Content-Type", "application/json")
                .header("Cookie", postAuthRequest.getToken())
                .when()
                .delete("booking/" + id);
    }
}
