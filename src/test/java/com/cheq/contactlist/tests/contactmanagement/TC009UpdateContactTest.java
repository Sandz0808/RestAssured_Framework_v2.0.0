package com.cheq.contactlist.tests.contactmanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.payloads.contacts.AddContactPayload;
import com.cheq.contactlist.payloads.users.CreateUserPayload;
import com.cheq.contactlist.services.UserService;
import com.cheq.contactlist.utilities.ApiAllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import com.cheq.contactlist.models.contactrequestmodel.UpdateContact;
import org.testng.annotations.Test;
import com.cheq.contactlist.payloads.contacts.UpdateContactPayload;
import com.cheq.contactlist.services.ContactService;


@Epic("Contact List API Testing")
@Feature("Add Contact Management")
public class TC009UpdateContactTest extends Hooks {


    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "contact", "test"},
            description = "TC009-Successful update contact"
    )
    public void testUpdateContactSuccessfully() {

        Response createUserResponse = UserService.createUser(CreateUserPayload.createValidUser(0));
        String dynamicToken = createUserResponse.jsonPath().getString("token");

        Response addContactResponse = ContactService.addContact(dynamicToken, AddContactPayload.createValidContact(0));
        String dynamicContactId = addContactResponse.jsonPath().getString("_id");

        UpdateContact payload = UpdateContactPayload.updateValidContact();
        Response response = ContactService.updateContact(dynamicToken, dynamicContactId, payload);

        ApiAllureUtil.steps("Validate Successful update contact");
        CommonAssertions.verifyStatusCode(response, 200);
        CommonAssertions.verifyHeader(response, "Content-Type", "application/json; charset=utf-8");
        CommonAssertions.verifyResponseTime(response, 2000);
        CommonAssertions.verifyBodyContainsText(response, "UPDATED");
        CommonAssertions.verifyResponseBody(response);




    }
}