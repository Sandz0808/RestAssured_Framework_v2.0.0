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
            groups = {"smoke", "auth", "debug"},
            description = "Verify that API rejects an invalid or expired authentication token"
    )
    public void invalidTokenTest() {

        UserService.createUser(CreateUserPayload.createValidUser(0));
        String token = "InvalidToken";

        Response response = UserService.readUser(token);

        CommonAssertions.verifyStatusCode(response, UNAUTHORIZED);
        AuthAssertions.verifyAuthorizationToken(token);
    }
}