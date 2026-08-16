package com.cheq.contactlist.tests.authentication;

import com.cheq.contactlist.assertions.authentication.AuthAssertions;
import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.models.userrequestmodel.LoginRequest;
import com.cheq.contactlist.payloads.authenticaion.LoginPayload;
import com.cheq.contactlist.services.AuthenticationService;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import static com.cheq.contactlist.constants.HeaderConstant.*;
import static com.cheq.contactlist.constants.StatusCodeConstant.*;


@Epic("Contact List API Testing")
@Feature("Authentication")
public class LoginSuccessfulTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "auth", "test"},
            description = "Validate Successful User logged in"
    )
    public void testSuccessfulUserLogin() {

        LoginRequest payload = LoginPayload.createLogin(0);
        Response response = AuthenticationService.loginUser(payload);

            Allure.step("Validate Successful Login");
            CommonAssertions.verifyStatusCode(response, OK);
            CommonAssertions.verifyHeader(response, CONTENT_TYPE, "application/json; charset=utf-8");
            CommonAssertions.verifyResponseTime(response, 2000);
            CommonAssertions.verifyBodyContainsText(response, "sjimena");
            CommonAssertions.verifyResponseBody(response);
            AuthAssertions.verifyTokenExists(response);


    }
}