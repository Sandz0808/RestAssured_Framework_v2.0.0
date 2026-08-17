package com.cheq.application.tests.contactlist.usermanagement;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.payloads.contactlistpayload.users.CreateUserPayload;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.models.contactlistmodel.userrequestmodel.UpdateUser;
import org.testng.annotations.Test;
import com.cheq.application.payloads.contactlistpayload.users.UpdateUserPayload;
import com.cheq.application.services.contactlistservice.UserService;


@Epic("Contact List API Testing")
@Feature("Add User Management")
public class UpdateUserTests extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "user", "test"},
            description = "Validate Successful update user"
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