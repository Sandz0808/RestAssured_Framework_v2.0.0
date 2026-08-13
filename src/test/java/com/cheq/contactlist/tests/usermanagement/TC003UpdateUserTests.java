package com.cheq.contactlist.tests.usermanagement;

import com.cheq.contactlist.assertions.common.*;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.payloads.users.CreateUserPayload;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import com.cheq.contactlist.models.userrequestmodel.UpdateUser;
import org.testng.annotations.Test;
import com.cheq.contactlist.payloads.users.UpdateUserPayload;
import com.cheq.contactlist.services.UserService;


@Epic("Contact List API Testing")
@Feature("Add User Management")
public class TC003UpdateUserTests extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "user", "test"},
            description = "TC003-Successful update user"
    )
    public void testUpdateUser() {

        Response createUserResponse = UserService.createUser(CreateUserPayload.createValidUser(0));
        String dynamicToken = createUserResponse.jsonPath().getString("token");

        UpdateUser payload = UpdateUserPayload.updateValidUser();
        Response response = UserService.updateUser(dynamicToken, payload);

        CommonAssertions.verifyStatusCode(response, 200);
        CommonAssertions.verifyHeader(response, "Content-Type", "application/json; charset=utf-8");
        CommonAssertions.verifyResponseTime(response, 2000);
        CommonAssertions.verifyBodyContainsText(response, "Updated_");
        CommonAssertions.verifyResponseBody(response);

    }
}