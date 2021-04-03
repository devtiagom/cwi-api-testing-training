package br.com.restassuredapitesting.tests.healthcheck.tests;

import br.com.restassuredapitesting.tests.base.tests.BaseTest;
import br.com.restassuredapitesting.tests.healthcheck.requests.HealthCheckRequest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;

public class HealthCheckTest extends BaseTest {

    HealthCheckRequest healthCheckRequest = new HealthCheckRequest();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Endpoint de health check para confirmar que a API está rodando.")
    public void verificarSaudeAPI() throws Exception {
        healthCheckRequest.ping().then()
                .statusCode(201)
                .body(containsString("Created"));
    }
}
