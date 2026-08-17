package com.cheq.application.tests.contactlist.authentication;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.assertions.validation.ValidationAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.payloads.contactlistpayload.authenticaion.LoginPayload;
import com.cheq.application.services.contactlistservice.AuthenticationService;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;


@Epic("Contact List API Testing")
@Feature("Authentication")
public class LogoutSuccessfulTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "auth", "test"},
            description = "Validate Successful User Logged out"
    )
    public void testSuccessfulUserLogout() {

        Response token = AuthenticationService.loginUser(LoginPayload.createLogin(0));

        Response response = AuthenticationService.logoutUser(token.jsonPath().getString("token"));
        CommonAssertions.verifyStatusCode(response, 200);
        ValidationAssertions.verifyResponseBodyIsEmpty(response);


    }
}