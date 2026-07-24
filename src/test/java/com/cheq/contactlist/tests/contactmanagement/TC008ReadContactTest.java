package com.cheq.contactlist.tests.contactmanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.assertions.validation.ValidationAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.payloads.contacts.ContactPayload;
import com.cheq.contactlist.payloads.users.CreateUserPayload;
import com.cheq.contactlist.services.UserService;
import com.cheq.contactlist.utils.ApiAllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import com.cheq.contactlist.services.ContactService;


@Epic("Contact List API Testing")
@Feature("AddContact Management")
public class TC008ReadContactTest extends Hooks {



    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "contact", "test"},
            description = "TC008-Successful read contact"
    )
    public void testReadContactSuccessfully() {

        Response createUserResponse = UserService.createUser(CreateUserPayload.createValidUser());
        String dynamicToken = createUserResponse.jsonPath().getString("token");

        Response addContactResponse = ContactService.addContact(dynamicToken, ContactPayload.createValidContact());
        String dynamicContactId = addContactResponse.jsonPath().getString("_id");

        Response response = ContactService.getContact(dynamicToken, dynamicContactId);

        ApiAllureUtil.steps("Validate Successful read contact");

        CommonAssertions.verifyStatusCode(response, 200);
        CommonAssertions.verifyHeader(response, "Content-Type", "application/json; charset=utf-8");
        CommonAssertions.verifyResponseTime(response, 2000);
        CommonAssertions.verifyBodyContainsText(response, "John");
        CommonAssertions.verifyResponseBody(response);

        ValidationAssertions.verifyFieldEquals(response, "firstName", "John");
        ValidationAssertions.verifyFieldExists(response, "email");



    }
}