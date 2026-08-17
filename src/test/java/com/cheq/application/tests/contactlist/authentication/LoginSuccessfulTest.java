package com.cheq.application.tests.contactlist.authentication;

import com.cheq.application.assertions.authentication.AuthAssertions;
import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.models.contactlistmodel.userrequestmodel.LoginRequest;
import com.cheq.application.payloads.contactlistpayload.authenticaion.LoginPayload;
import com.cheq.application.services.contactlistservice.AuthenticationService;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.application.listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import static com.cheq.application.constants.contactlistconstant.HeaderConstant.*;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;


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