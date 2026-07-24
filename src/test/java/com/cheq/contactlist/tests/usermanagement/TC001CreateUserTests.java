package com.cheq.contactlist.tests.usermanagement;

import com.cheq.contactlist.assertions.auth.AuthAssertions;
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


@Epic("Contact List API Testing")
@Feature("User Management")
public class TC001CreateUserTests extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"sanity", "user", "test"},
            description = "TC-001 Successful user creation"
    )

    public void testSuccessfulUserSignup() {

        CreateUser payload = CreateUserPayload.createValidUser();
        Response response = UserService.createUser(payload);

        CommonAssertions.verifyStatusCode(response, 201);
        CommonAssertions.verifyHeader(response, "Content-Type", "application/json; charset=utf-8");
        CommonAssertions.verifyResponseTime(response, 2000);
        CommonAssertions.verifyBodyContainsText(response, "Sandro");
        CommonAssertions.verifyResponseBody(response);
        AuthAssertions.verifyAuthorizationToken(response.jsonPath().getString("token"));

        SchemaAssertions.verifySchema(response, SchemaAssertions.SchemaType.CREATE_USER_SCHEMA);

    }
}