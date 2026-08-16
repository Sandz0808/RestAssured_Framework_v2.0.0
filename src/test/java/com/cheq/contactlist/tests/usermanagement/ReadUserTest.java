package com.cheq.contactlist.tests.usermanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.payloads.users.CreateUserPayload;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import com.cheq.contactlist.services.UserService;
import static com.cheq.contactlist.constants.HeaderConstant.*;
import static com.cheq.contactlist.constants.StatusCodeConstant.*;


@Epic("Contact List API Testing")
@Feature("Add User Management")
public class ReadUserTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "user", "test"},
            description = "Validate Successful read user"
    )
    public void testReadUser() {

        Response createUserResponse = UserService.createUser(CreateUserPayload.createValidUser(0));
        String dynamicToken = createUserResponse.jsonPath().getString("token");

        Response response = UserService.readUser(dynamicToken);

        CommonAssertions.verifyStatusCode(response, OK);
        CommonAssertions.verifyHeader(response, CONTENT_TYPE, "application/json; charset=utf-8");
        CommonAssertions.verifyResponseTime(response, 2000);
        CommonAssertions.verifyBodyContainsText(response, "Sandrex");
        CommonAssertions.verifyResponseBody(response);
        CommonAssertions.verifyResponseHeaders(response);



    }
}