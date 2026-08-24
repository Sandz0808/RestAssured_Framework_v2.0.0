package com.cheq.application.tests.contactlist.authentication;

import com.cheq.application.assertions.authentication.AuthAssertions;
import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.payloads.contactlistpayload.users.CreateUserPayload;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.application.listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import com.cheq.application.services.contactlistservice.UserService;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;


@Epic("Contact List API Testing")
@Feature("Authentication")
public class InvalidTokenTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"regression", "smoke", "auth", "test", "critical"},
            description = "TC-Auth-001 - Validated Invalid Token Test"
    )
    public void invalidTokenTest() {

        // INDEX 0 FOR VALID USER
        int validUser = 0;

        UserService.createUser(CreateUserPayload.createValidUser(validUser));
        String token = "InvalidToken";

        Response response = UserService.readUser(token);

        Allure.step("Verify that API rejects an invalid or expired authentication token");
        CommonAssertions.verifyStatusCode(response, UNAUTHORIZED);
        AuthAssertions.verifyAuthorizationToken(token);
    }
}