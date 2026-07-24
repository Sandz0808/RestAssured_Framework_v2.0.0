package com.cheq.contactlist.tests.authmanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import com.cheq.contactlist.payloads.users.LoginPayload;
import com.cheq.contactlist.services.AuthService;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import com.cheq.contactlist.services.UserService;


@Epic("Contact List API Testing")
@Feature("User Management")
public class TC006LogoutTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "user", "test"},
            description = "TC006-Successful user logout"
    )
    public void testSuccessfulUserLogout() {

        Response token = AuthService.loginUser(LoginPayload.createValidLogin());

        Response response = AuthService.logoutUser(token.jsonPath().getString("token"));

        CommonAssertions.verifyStatusCode(response, 200);


    }
}