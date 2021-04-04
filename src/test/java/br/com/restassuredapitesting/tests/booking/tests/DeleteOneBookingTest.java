package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.suites.Acceptance;
import br.com.restassuredapitesting.tests.base.tests.BaseTest;
import br.com.restassuredapitesting.tests.booking.requests.DeleteOneBookingRequest;
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
public class DeleteOneBookingTest extends BaseTest {

    GetBookingRequest getBookingRequest = new GetBookingRequest();
    DeleteOneBookingRequest deleteOneBookingRequest = new DeleteOneBookingRequest();

    // Este teste falha porque a API está retornando status http 201 (Created),
    // e me pareceu mais coerente esperar um status 200 (Ok). Neste caso reportaria um bug.
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(Acceptance.class)
    @DisplayName("Deletar uma reserva utilizando token")
    public void validarDeletarUmaReservaUtilizandoToken() {
        int primeiroId = getBookingRequest.allBookings()
                .then()
                .statusCode(200)
                .extract()
                .path("[0].bookingid");

        deleteOneBookingRequest.deletarUmaReservaComToken(primeiroId).then()
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body(containsString("Deleted"));
    }
}
