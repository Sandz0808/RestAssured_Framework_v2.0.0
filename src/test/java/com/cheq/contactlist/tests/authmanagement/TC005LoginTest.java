package com.cheq.contactlist.tests.authmanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.models.userrequestmodel.LoginRequest;
import com.cheq.contactlist.payloads.users.LoginPayload;
import com.cheq.contactlist.services.AuthService;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import com.cheq.contactlist.services.UserService;


@Epic("Contact List API Testing")
@Feature("User Management")
public class TC005LoginTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "user", "test"},
            description = "TC005-Successful user login"
    )
    public void testSuccessfulUserLogin() {

        LoginRequest payload = LoginPayload.createValidLogin();

        Response response = AuthService.loginUser(payload);

        CommonAssertions.verifyStatusCode(response, 200);
        CommonAssertions.verifyHeader(response, "Content-Type", "application/json; charset=utf-8");
        CommonAssertions.verifyResponseTime(response, 2000);
        CommonAssertions.verifyBodyContainsText(response, "sjimena");
        CommonAssertions.verifyResponseBody(response);

    }
}