package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.suites.Acceptance;
import br.com.restassuredapitesting.suites.E2e;
import br.com.restassuredapitesting.tests.base.tests.BaseTest;
import br.com.restassuredapitesting.tests.booking.requests.DeleteBookingRequest;
import br.com.restassuredapitesting.tests.booking.requests.GetBookingRequest;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.lessThan;

@Feature("Reservas")
public class DeleteBookingTest extends BaseTest {

    GetBookingRequest getBookingRequest = new GetBookingRequest();
    DeleteBookingRequest deleteBookingRequest = new DeleteBookingRequest();

    int primeiroId = getBookingRequest.allBookings()
            .then()
            .statusCode(200)
            .extract()
            .path("[0].bookingid");

    // Este teste falha porque a API está retornando status http 201 (Created),
    // e me pareceu mais coerente esperar um status 200 (Ok). Neste caso reportaria um bug.
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(Acceptance.class)
    @DisplayName("Deletar uma reserva utilizando token")
    public void validarDeletarUmaReservaUtilizandoToken() {
        deleteBookingRequest.deletarUmaReservaComToken(primeiroId).then()
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body(containsString("Deleted"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(E2e.class)
    @DisplayName("Tentar deletar uma reserva sem autorização")
    public void validarDeletarUmaReservaSemToken() {
        deleteBookingRequest.deletarUmaReservaSemToken(primeiroId).then()
                .statusCode(403)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body(containsString("Forbidden"));
    }

    // Este teste falha porque a API está retornando status http 405 (Method Not Allowed),
    // e me pareceu mais coerente esperar um status 401 (Not Found). Neste caso reportaria um bug.
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(E2e.class)
    @DisplayName("Tentar deletar uma reserva que não existe")
    public void validarDeletarUmaReservaInvalidaUtilizandoToken() {
        deleteBookingRequest.deletarUmaReservaComToken(1234).then()
                .statusCode(401)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body(containsString("Not Found"));
    }
}
