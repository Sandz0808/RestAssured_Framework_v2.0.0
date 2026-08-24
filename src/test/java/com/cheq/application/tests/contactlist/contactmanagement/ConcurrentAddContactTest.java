package com.cheq.application.tests.contactlist.contactmanagement;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.models.contactlistmodel.contactrequestmodel.CreateContact;
import com.cheq.application.payloads.contactlistpayload.contacts.AddContactPayload;
import com.cheq.application.tests.contactlist.reusables.ReusableTest;
import com.cheq.application.utilities.AllureUtil;
import com.cheq.application.utilities.ConcurrentRequestUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.application.listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import com.cheq.application.services.contactlistservice.ContactService;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;


@Epic("Contact List API Testing")
@Feature("Add Contact Management")
public class ConcurrentAddContactTest extends Hooks {


    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"regression", "smoke", "contact", "test"},
            description = "TC-Contact-002 - Validate No Replay Back for Add Contact"
    )
    public void testConcurrentRequests() {

        // SIGN UP / CREATE USER
        Response signUpResponse = ReusableTest.signUp(0);

        String token = signUpResponse.jsonPath().getString("token");

        // ADD CONTACT / CREATE CONTACT
        CreateContact payload =  AddContactPayload.createValidContact(0);

        String idempotencyKey = UUID.randomUUID().toString();

        // REQUEST 1
        CompletableFuture<Response> request1 =
                ConcurrentRequestUtil.executeAsync(() ->
                        ContactService.addContactWithIdempotencyKey(
                                token,
                                payload,
                                idempotencyKey));
        // REQUEST 2
        CompletableFuture<Response> request2 =
                ConcurrentRequestUtil.executeAsync(() ->
                        ContactService.addContactWithIdempotencyKey(
                                token,
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
        AllureUtil.steps("Validate No Replay Back for Add Contact");
        CommonAssertions.verifyStatusCode(response1, CREATED);
        CommonAssertions.verifyStatusCode(response2, CONFLICT);
    }
}