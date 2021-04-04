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
public class GetBookingTest extends BaseTest {

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
    @DisplayName("Listar IDs das reservas")
    public void validarIdsDasReservas() throws Exception {
        getBookingRequest.allBookings().then()
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body("size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Category(Contract.class)
    @DisplayName("Garantir o contrato de retorno da lista de reservas")
    public void garantirContratoListaReserva() throws Exception {
        getBookingRequest.allBookings().then()
                .statusCode(200)
                .assertThat()
                .body(
                        matchesJsonSchema(
                                new File(
                                        Utils.getContractsBasePath("booking", "bookings")
                                )
                        )
                );
    }

    // Antes de testar o endpoint busquei um primeiro nome que existe nas reservas,
    // para garantir que devesse esperar sucesso no teste
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(Acceptance.class)
    @DisplayName("Listar reservas filtrando por primeiro nome")
    public void ValidarIdsDasReservasFiltrandoPorPrimeiroNome() {
        String primeiroNome = getOneBookingRequest.oneBooking(primeiroId)
                .then()
                .statusCode(200)
                .extract()
                .path("firstname");

        getBookingRequest.allBookingsByOneParam("firstname", primeiroNome).then()
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body("size()", greaterThan(0));
    }

    // Antes de testar o endpoint busquei um último nome que existe nas reservas,
    // para garantir que devesse esperar sucesso no teste
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(Acceptance.class)
    @DisplayName("Listar reservas filtrando por último nome")
    public void ValidarIdsDasReservasFiltrandoPorUltimoNome() {
        String ultimoNome = getOneBookingRequest.oneBooking(primeiroId)
                .then()
                .statusCode(200)
                .extract()
                .path("lasttname");

        getBookingRequest.allBookingsByOneParam("lasttname", ultimoNome).then()
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body("size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(Acceptance.class)
    @DisplayName("Listar reservas filtrando por data de checkin")
    public void ValidarIdsDasReservasFiltrandoPorCheckin() {
        getBookingRequest.allBookingsByOneParam("checkin", "2018-01-01").then()
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body("size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(Acceptance.class)
    @DisplayName("Listar reservas filtrando por data de checkout")
    public void ValidarIdsDasReservasFiltrandoPorCheckout() {
        getBookingRequest.allBookingsByOneParam("checkout", "2018-01-01").then()
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body("size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(Acceptance.class)
    @DisplayName("Listar reservas filtrando por datas de checkin e checkout")
    public void ValidarIdsDasReservasFiltrandoPorCheckinECheckout() {
        getBookingRequest.allBookingsByCheckinAndCheckout("2018-01-01", "2018-01-15")
                .then()
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body("size()", greaterThan(0));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Category(Acceptance.class)
    @DisplayName("Listar reservas filtrando por nome e datas de checkin e checkout")
    public void ValidarIdsDasReservasFiltrandoPorNomeECheckinECheckout() {
        getBookingRequest.allBookingsByNameAndCheckinAndCheckout("Julia", "2018-01-01", "2018-01-15")
                .then()
                .statusCode(200)
                .time(lessThan(2L), TimeUnit.SECONDS)
                .body("size()", greaterThan(0));
    }
}
