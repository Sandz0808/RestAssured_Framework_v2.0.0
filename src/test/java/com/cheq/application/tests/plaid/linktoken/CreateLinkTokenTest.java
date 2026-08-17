package com.cheq.application.tests.plaid.linktoken;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.assertions.validation.ValidationAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.services.plaidservice.LinkTokenService;
import com.cheq.application.utilities.AllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;


@Epic("Plaid API Testing")
@Feature("Link Management")
public class CreateLinkTokenTest extends Hooks {


    @Test(
            priority = 1,
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "plaid", "test"},
            description = "Validate Successful Link Token Creation"
    )
    public void testCreateLinkTokenSuccessfully() {

        // CREATE LINK TOKEN
        Response response =  LinkTokenService.createLinkToken();

        // ASSERTIONS
        AllureUtil.steps("Validate Successful Link Token Creation");
        CommonAssertions.verifyStatusCode(response, 200);
        CommonAssertions.verifyHeader(response, "Content-Type", "application/json; charset=utf-8" );
        //CommonAssertions.verifyResponseTime(response, 2000);
        ValidationAssertions.verifyFieldExists(response, "link_token");
        ValidationAssertions.verifyFieldExists(response, "expiration");
        ValidationAssertions.verifyFieldExists(response, "request_id" );
        CommonAssertions.verifyResponseBody(response);
    }
}