package com.cheq.contactlist.tests.authentication;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.assertions.validation.ValidationAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import com.cheq.contactlist.payloads.authenticaion.LoginPayload;
import com.cheq.contactlist.services.AuthenticationService;
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