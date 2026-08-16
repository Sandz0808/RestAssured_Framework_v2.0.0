package com.cheq.contactlist.tests.usermanagement;

import com.cheq.contactlist.assertions.authentication.AuthAssertions;
import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.assertions.schema.SchemaAssertions;
import com.cheq.contactlist.hooks.Hooks;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import com.cheq.contactlist.models.userrequestmodel.CreateUser;
import org.testng.annotations.Test;
import com.cheq.contactlist.payloads.users.CreateUserPayload;
import com.cheq.contactlist.services.UserService;

import static com.cheq.contactlist.constants.HeaderConstant.*;
import static com.cheq.contactlist.constants.StatusCodeConstant.*;


@Epic("Contact List API Testing")
@Feature("Add User Management")
public class CreateUserTests extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"sanity", "user", "test",},
            description = "Validate Successful user creation"
    )

    public void testSuccessfulUserSignup() {

        CreateUser payload = CreateUserPayload.createValidUser(0);
        Response response = UserService.createUser(payload);

        CommonAssertions.verifyStatusCode(response, CREATED);
        CommonAssertions.verifyHeader(response, CONTENT_TYPE, "application/json; charset=utf-8");
        CommonAssertions.verifyResponseTime(response, 2000);
        CommonAssertions.verifyBodyContainsText(response, "Sandrex");
        CommonAssertions.verifyResponseBody(response);
        AuthAssertions.verifyAuthorizationToken(response.jsonPath().getString("token"));

        SchemaAssertions.verifySchema(response, SchemaAssertions.SchemaType.CREATE_USER_SCHEMA);

    }
}