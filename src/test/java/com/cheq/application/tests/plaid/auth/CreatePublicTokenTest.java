package com.cheq.application.tests.plaid.auth;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.assertions.validation.ValidationAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.models.plaidmodel.authenticationmodel.CreatePublicToken;
import com.cheq.application.models.plaidmodel.authenticationmodel.PublicTokenOptions;
import com.cheq.application.services.plaidservice.AuthenticationService;
import com.cheq.application.utilities.AllureUtil;
import com.cheq.application.utilities.ConfigReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Plaid API Testing")
@Feature("Authentication")
public class CreatePublicTokenTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "plaid", "test"},
            description = "Validate successful creation of Public Token"
    )
    public void testCreatePublicTokenSuccessfully() {

        PublicTokenOptions options = new PublicTokenOptions();
        String genericWebhookUrl = ConfigReader.get("plaid.webhook");
        options.setWebhook(genericWebhookUrl);

        CreatePublicToken payload = new CreatePublicToken();

        payload.setClient_id(ConfigReader.get("plaid.client.id"));
        payload.setSecret(ConfigReader.get("plaid.client.secret"));
        payload.setInstitution_id("ins_20");
        payload.setInitial_products(List.of("auth"));
        payload.setOptions(options);

        Response response = AuthenticationService.createPublicToken(payload);


        AllureUtil.steps("Validate Successful Public Token Creation");
        CommonAssertions.verifyStatusCode(response, 200);
        CommonAssertions.verifyHeader(response, "Content-Type", "application/json; charset=utf-8" );
        //CommonAssertions.verifyResponseTime(response, 2000);
        ValidationAssertions.verifyFieldExists(response, "public_token");
        ValidationAssertions.verifyFieldExists(response, "request_id");
        CommonAssertions.verifyResponseBody(response);


    }
}