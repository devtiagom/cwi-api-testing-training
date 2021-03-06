package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.suites.Acceptance;
import br.com.restassuredapitesting.suites.Contract;
import br.com.restassuredapitesting.tests.base.tests.BaseTest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.GetOneBookingRequest;
import br.com.restassuredapitesting.utils.Utils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

@Feature("Reservas")
public class GetOneBookingTest extends BaseTest {

    GetBookingRequest getBookingRequest = new GetBookingRequest();
    GetOneBookingRequest getOneBookingRequest = new GetOneBookingRequest();

    int primeiroId = getBookingRequest.allBookings()
            .then()
            .statusCode(200)
            .extract()
            .path("[0].bookingid");

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(Acceptance.class)
    @DisplayName("Mostrar uma reserva específica")
    public void validarUmaReservaEspecifica() {
        getOneBookingRequest.oneBooking(primeiroId).then()
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body("size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category(Contract.class)
    @DisplayName("Garantir o contrato de retorno de uma reserva específica")
    public void garantirContratoDeUmaReserva() throws Exception {
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
