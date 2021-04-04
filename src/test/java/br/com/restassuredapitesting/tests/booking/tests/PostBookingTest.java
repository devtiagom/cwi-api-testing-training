package br.com.restassuredapitesting.tests.booking.tests;

import br.com.restassuredapitesting.suites.Acceptance;
import br.com.restassuredapitesting.suites.E2e;
import br.com.restassuredapitesting.tests.base.tests.BaseTest;
import br.com.restassuredapitesting.tests.booking.requests.PostBookingRequest;
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
public class PostBookingTest extends BaseTest {

    PostBookingRequest postBookingRequest = new PostBookingRequest();

    // Este teste falha porque a API está retornando status http 200 (Ok),
    // e me pareceu mais coerente esperar um status 201 (Created). Neste caso reportaria um bug.
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(Acceptance.class)
    @DisplayName("Criar uma nova reserva")
    public void validarCriarUmaNovaReserva() throws Exception {
        postBookingRequest.newBooking(Utils.validPayloadBooking()).then()
                .statusCode(201)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body("size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(E2e.class)
    @DisplayName("Tentar criar uma nova reserva com payload inválido")
    public void validarCriarUmaNovaReservaComPayloadInvalido() throws Exception {
        postBookingRequest.newBooking(Utils.invalidPayloadBooking()).then()
                .statusCode(500)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body(containsString("Internal Server Error"));
    }
}
