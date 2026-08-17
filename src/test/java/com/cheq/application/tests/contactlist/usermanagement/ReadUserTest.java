package com.cheq.application.tests.contactlist.usermanagement;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.payloads.contactlistpayload.users.CreateUserPayload;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.application.listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import com.cheq.application.services.contactlistservice.UserService;
import static com.cheq.application.constants.contactlistconstant.ContactListHeaderConstant.*;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;


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