package com.cheq.application.tests.plaid.linktoken;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.assertions.validation.ValidationAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.services.plaidservice.LinkTokenService;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.utilities.AllureUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static com.cheq.application.constants.contactlistconstant.ContactListHeaderConstant.*;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;

@Epic("Plaid API Testing")
@Feature("Link Management")
public class CreateHostedLinkTokenTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"regression", "smoke", "plaid", "test"},
            description = "TC-Plaid-LinkToken-001 - Validate successful creation of Hosted Link Token"
    )
    public void testCreateHostedLinkTokenSuccessfully() {

        Response response = LinkTokenService.createHostedLinkToken();

        AllureUtil.steps("Validate Successful Hosted Link Token Creation");
        CommonAssertions.verifyStatusCode(response, OK);
        CommonAssertions.verifyHeader(response, CONTENT_TYPE, CONTENT_TYPE_HEADER);
        ValidationAssertions.verifyFieldExists(response, "link_token");
        ValidationAssertions.verifyFieldExists(response, "expiration");
        ValidationAssertions.verifyFieldExists(response, "request_id" );
        ValidationAssertions.verifyFieldExists(response, "hosted_link_url" );
        CommonAssertions.verifyResponseBody(response);
    }
}