package com.cheq.contactlist.tests.usermanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.models.userrequestmodel.CreateUser;
import com.cheq.contactlist.payloads.users.CreateUserPayload;
import com.cheq.contactlist.services.UserService;
import com.cheq.contactlist.utilities.ConcurrentRequestUtil;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Epic("Contact List API Testing")
@Feature("Add User Management")
public class ConcurrentAddUserTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "user", "test"},
            description = "Validate No Replay Back for Create User"
    )
    public void testConcurrentRequests() {

        // SIGN UP / CREATE USER
        CreateUser payload =
                CreateUserPayload.createValidUser(0);

        String idempotencyKey =
                UUID.randomUUID().toString();

        // REQUEST 1
        CompletableFuture<Response> request1 =
                ConcurrentRequestUtil.executeAsync(() ->
                        UserService.createUserWithIdempotencyKey(
                                payload,
                                idempotencyKey));

        // REQUEST 2
        CompletableFuture<Response> request2 =
                ConcurrentRequestUtil.executeAsync(() ->
                        UserService.createUserWithIdempotencyKey(
                                payload,
                                idempotencyKey));

        // WAIT FOR BOTH REQUESTS
        ConcurrentRequestUtil.waitForAll(
                request1,
                request2);

        Response response1 = request1.join();
        Response response2 = request2.join();

        System.out.println("Status 1: " + response1.statusCode());
        System.out.println("Status 2: " + response2.statusCode());

        // ASSERTIONS
        CommonAssertions.verifyStatusCode(
                response1,
                201
        );

        CommonAssertions.verifyStatusCode(
                response2,
                409
        );
    }
}