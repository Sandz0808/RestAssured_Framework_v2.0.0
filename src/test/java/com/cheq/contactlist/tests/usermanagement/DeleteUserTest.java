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
@Feature("Add User Management")
public class DeleteUserTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "user", "test"},
            description = "Validate Successful delete user"
    )
    public void testDeleteUser() {

        Response createUserResponse = UserService.createUser(CreateUserPayload.createValidUser(0));
        String dynamicToken = createUserResponse.jsonPath().getString("token");

        Response response = UserService.deleteUser(dynamicToken);
        CommonAssertions.verifyStatusCode(response, 200);
    }
}