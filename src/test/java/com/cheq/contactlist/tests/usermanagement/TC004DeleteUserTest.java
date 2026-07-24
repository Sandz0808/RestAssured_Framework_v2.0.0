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
public class TC004DeleteUserTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "user", "test"},
            description = "TC004-Successful delete user"
    )
    public void testDeleteUser() {

        Response createUserResponse = UserService.createUser(CreateUserPayload.createValidUser());
        String dynamicToken = createUserResponse.jsonPath().getString("token");

        Response response = UserService.deleteUser(dynamicToken);
        CommonAssertions.verifyStatusCode(response, 200);
    }
}