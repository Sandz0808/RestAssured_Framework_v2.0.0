package com.cheq.contactlist.tests.contactmanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import com.cheq.contactlist.utils.ApiAllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import com.cheq.contactlist.payloads.contacts.ContactPayload;
import com.cheq.contactlist.payloads.users.CreateUserPayload;
import com.cheq.contactlist.services.ContactService;
import com.cheq.contactlist.services.UserService;


@Epic("Contact List API Testing")
@Feature("AddContact Management")
public class TC010DeleteContactTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "contact", "test"},
            description = "TC010-Successful delete contact"
    )
    public void testDeleteContactSuccessfully() {

        Response createUserResponse = UserService.createUser(CreateUserPayload.createValidUser());
        String dynamicToken = createUserResponse.jsonPath().getString("token");

        Response addContactResponse = ContactService.addContact(dynamicToken, ContactPayload.createValidContact());
        String dynamicContactId = addContactResponse.jsonPath().getString("_id");

        Response response = ContactService.deleteContact(dynamicToken, dynamicContactId);

        ApiAllureUtil.steps("Validate Successful delete contact");
        CommonAssertions.verifyStatusCode(response, 200);

    }
}