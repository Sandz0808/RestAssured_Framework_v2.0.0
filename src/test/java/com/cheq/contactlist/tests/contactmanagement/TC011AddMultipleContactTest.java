package com.cheq.contactlist.tests.contactmanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.assertions.schema.SchemaAssertions;
import com.cheq.contactlist.assertions.validation.ValidationAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.payloads.users.CreateUserPayload;
import com.cheq.contactlist.services.UserService;
import com.cheq.contactlist.utils.ApiAllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import com.cheq.contactlist.models.contactrequestmodel.CreateContact;
import org.testng.annotations.Test;
import com.cheq.contactlist.payloads.contacts.ContactPayload;
import com.cheq.contactlist.services.ContactService;


@Epic("Contact List API Testing")
@Feature("AddContact Management")
public class TC011AddMultipleContactTest extends Hooks {


    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "contact", "test"},
            description = "TC011-Successful add multiple contact"
    )
    public void testAddMultipleContactSuccessfully() {

        Response createUserResponse = UserService.createUser(CreateUserPayload.createValidUser());
        String dynamicToken = createUserResponse.jsonPath().getString("token");

        CreateContact payload = ContactPayload.createValidContact();

        for (int i = 1; i <= 5; i++) {
            Response response = ContactService.addContact(dynamicToken, payload);

        ApiAllureUtil.steps("Validate Successful contact creation:     " + i + "");
        CommonAssertions.verifyStatusCode(response, 201);
        CommonAssertions.verifyHeader(response, "Content-Type", "application/json; charset=utf-8");
        CommonAssertions.verifyResponseTime(response, 2000);
        CommonAssertions.verifyBodyContainsText(response, "John");
        CommonAssertions.verifyResponseBody(response);
        SchemaAssertions.verifySchema(response, SchemaAssertions.SchemaType.CREATE_CONTACT_SCHEMA);

        ValidationAssertions.verifyFieldEquals(response, "firstName", "John");
        ValidationAssertions.verifyFieldExists(response, "email");

        }



    }
}