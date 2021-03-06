package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.suites.Acceptance;
import br.com.restassuredapitesting.suites.E2e;
import br.com.restassuredapitesting.tests.base.tests.BaseTest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.PutBookingRequest;
import br.com.restassuredapitesting.utils.Utils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

@Feature("Reservas")
public class PutBookingTest extends BaseTest {

    GetBookingRequest getBookingRequest = new GetBookingRequest();
    PutBookingRequest putBookingRequest = new PutBookingRequest();

    int primeiroId = getBookingRequest.allBookings()
            .then()
            .statusCode(200)
            .extract()
            .path("[0].bookingid");

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(Acceptance.class)
    @DisplayName("Alterar uma reserva utilizando token")
    public void validarAlterarUmaReservaUtilizandoToken() throws Exception {
        putBookingRequest.alterarUmaReservaComToken(primeiroId, Utils.validPayloadBooking()).then()
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body("size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(E2e.class)
    @DisplayName("Tentar alterar uma reserva sem token")
    public void validarAlterarUmaReservaSemToken() throws Exception {
        putBookingRequest.alterarUmaReservaSemToken(primeiroId, Utils.validPayloadBooking()).then()
                .statusCode(403)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body(containsString("Forbidden"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(E2e.class)
    @DisplayName("Alterar uma reserva utilizando token inv??lido")
    public void validarAlterarUmaReservaUtilizandoTokenInvalido() throws Exception {
        putBookingRequest.alterarUmaReservaComTokenInvalido(primeiroId, Utils.validPayloadBooking())
                .then()
                .statusCode(403)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body(containsString("Forbidden"));
    }

    // Este teste falha porque a API est?? retornando status http 405 (Method Not Allowed),
    // e me pareceu mais coerente esperar um status 401 (Not Found). Neste caso reportaria um bug.
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(E2e.class)
    @DisplayName("Tentar alterar uma reserva que n??o existe")
    public void validarAlterarUmaReservaInexistente() throws Exception {
        putBookingRequest.alterarUmaReservaComToken(1234, Utils.validPayloadBooking()).then()
                .statusCode(401)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body(containsString("Not Found"));
    }
}
