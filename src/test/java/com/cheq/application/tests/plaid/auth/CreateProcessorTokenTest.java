package com.cheq.application.tests.plaid.auth;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.assertions.validation.ValidationAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.models.plaidmodel.authenticationmodel.CreateProcessorToken;
import com.cheq.application.models.plaidmodel.authenticationmodel.ProcessorTokenOptions;
import com.cheq.application.services.plaidservice.AuthenticationService;
import com.cheq.application.utilities.AllureUtil;
import com.cheq.application.utilities.ConfigReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.cheq.application.constants.contactlistconstant.ContactListHeaderConstant.CONTENT_TYPE;
import static com.cheq.application.constants.contactlistconstant.ContactListHeaderConstant.CONTENT_TYPE_HEADER;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;

@Epic("Plaid API Testing")
@Feature("Authentication")
public class CreateProcessorTokenTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "plaid", "test"},
            description = "TC-Plaid-Auth-001 - Validate successful creation of Processor Token"
    )
    public void testCreateProcessorTokenSuccessfully() {

        ProcessorTokenOptions options = new ProcessorTokenOptions();

        options.setOverride_username("user_good");
        options.setOverride_password("pass_good");

        CreateProcessorToken payload = new CreateProcessorToken();

        payload.setClient_id(ConfigReader.get("plaid.client.id"));
        payload.setSecret(ConfigReader.get("plaid.client.secret"));
        payload.setInstitution_id("ins_3");
        payload.setOptions(options);

        Response response = AuthenticationService.createProcessorToken(payload);

        AllureUtil.steps("Validate Successful Processor  Token Creation");
        CommonAssertions.verifyStatusCode(response, OK);
        CommonAssertions.verifyHeader(response, CONTENT_TYPE, CONTENT_TYPE_HEADER);
        ValidationAssertions.verifyFieldExists(response, "processor_token");
        ValidationAssertions.verifyFieldExists(response, "request_id");
        CommonAssertions.verifyResponseBody(response);

    }
}