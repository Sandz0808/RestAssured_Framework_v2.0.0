package com.cheq.application.tests.contactlist.usermanagement;

import com.cheq.application.assertions.authentication.AuthAssertions;
import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.assertions.schema.SchemaAssertions;
import com.cheq.application.hooks.Hooks;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.models.contactlistmodel.userrequestmodel.CreateUser;
import org.testng.annotations.Test;
import com.cheq.application.payloads.contactlistpayload.users.CreateUserPayload;
import com.cheq.application.services.contactlistservice.UserService;

import static com.cheq.application.constants.contactlistconstant.HeaderConstant.*;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;


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