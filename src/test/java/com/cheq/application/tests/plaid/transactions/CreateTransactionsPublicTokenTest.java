package com.cheq.application.tests.plaid.transactions;

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
import static com.cheq.application.constants.contactlistconstant.ContactListHeaderConstant.*;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;

@Epic("Plaid API Testing")
@Feature("Transactions")
public class CreateTransactionsPublicTokenTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"regression", "plaid, test"},
            description = "TC-Plaid-Transaction-001 - Validate successful creation of Transactions Public Token"
    )
    public void testCreateTransactionsPublicTokenSuccessfully() {

        // Options
        PublicTokenOptions options = new PublicTokenOptions();

        String webHook = ConfigReader.get("plaid.webhook");
        options.setWebhook(webHook);

        options.setOverride_username("user_transactions_dynamic");
        options.setOverride_password("test");

        // Request Payload
        CreatePublicToken payload = new CreatePublicToken();

        payload.setClient_id(ConfigReader.get("plaid.client.id") );
        payload.setSecret(ConfigReader.get("plaid.client.secret"));

        payload.setInstitution_id("ins_20");

        payload.setInitial_products(List.of("transactions"));

        payload.setOptions(options);

        // API Request
        Response response = AuthenticationService.createPublicToken(payload);

        // Assertion
        AllureUtil.steps("Validate Successful Transaction Public Token Creation");
        CommonAssertions.verifyStatusCode(response, OK);
        CommonAssertions.verifyHeader(response, CONTENT_TYPE, CONTENT_TYPE_HEADER);
        ValidationAssertions.verifyFieldExists(response, "public_token");
        ValidationAssertions.verifyFieldExists(response, "request_id");
        CommonAssertions.verifyResponseBody(response);
    }
}