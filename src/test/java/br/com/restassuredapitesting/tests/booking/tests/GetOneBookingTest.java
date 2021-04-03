package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.tests.base.tests.BaseTest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.GetOneBookingRequest;
import br.com.restassuredapitesting.utils.Utils;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class GetOneBookingTest extends BaseTest {

    GetBookingRequest getBookingRequest = new GetBookingRequest();
    GetOneBookingRequest getOneBookingRequest = new GetOneBookingRequest();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Garantir o contrato de retorno de uma reserva específica")
    public void garantirContratoDeUmaReserva() throws Exception {
        int primeiroId = getBookingRequest.allBookings()
                .then()
                .statusCode(200)
                .extract()
                .path("[0].bookingid");

        getOneBookingRequest.oneBooking(primeiroId).then()
                .statusCode(200)
                .assertThat()
                .body(
                        matchesJsonSchema(
                                new File(Utils.getContractsBasePath("booking", "booking"))
                        )
                );
    }
}
