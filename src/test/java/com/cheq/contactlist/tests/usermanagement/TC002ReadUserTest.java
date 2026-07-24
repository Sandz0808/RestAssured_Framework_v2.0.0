package com.cheq.contactlist.tests.usermanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.payloads.users.CreateUserPayload;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import com.cheq.contactlist.services.UserService;


@Epic("Contact List API Testing")
@Feature("User Management")
public class TC002ReadUserTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "user", "debug"},
            description = "TC002-Successful read user"
    )
    public void testReadUser() {

        Response createUserResponse = UserService.createUser(CreateUserPayload.createValidUser());
        String dynamicToken = createUserResponse.jsonPath().getString("token");

        Response response = UserService.readUser(dynamicToken);

        CommonAssertions.verifyStatusCode(response, 200);
        CommonAssertions.verifyHeader(response, "Content-Type", "application/json; charset=utf-8");
        CommonAssertions.verifyResponseTime(response, 2000);
        CommonAssertions.verifyBodyContainsText(response, "Sandro");
        CommonAssertions.verifyResponseBody(response);
        CommonAssertions.verifyResponseHeaders(response);



    }
}