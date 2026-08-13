package com.cheq.contactlist.tests.authentication;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import com.cheq.contactlist.payloads.authenticaion.LoginPayload;
import com.cheq.contactlist.services.AuthService;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;


@Epic("Contact List API Testing")
@Feature("Authentication")
public class TC006LogoutTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "auth", "test"},
            description = "TC006-Successful user logout"
    )
    public void testSuccessfulUserLogout() {

        Response token = AuthService.loginUser(LoginPayload.createLogin(0));

        Response response = AuthService.logoutUser(token.jsonPath().getString("token"));

        CommonAssertions.verifyStatusCode(response, 200);


    }
}