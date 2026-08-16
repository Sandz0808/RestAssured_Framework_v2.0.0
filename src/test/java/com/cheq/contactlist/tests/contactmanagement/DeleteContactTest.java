package com.cheq.contactlist.tests.contactmanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import com.cheq.contactlist.tests.reusables.ReusableTest;
import com.cheq.contactlist.utilities.AllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import com.cheq.contactlist.services.ContactService;


@Epic("Contact List API Testing")
@Feature("Add Contact Management")
public class DeleteContactTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "contact", "test"},
            description = "Validate Successful Delete Contact"
    )
    public void testDeleteContactSuccessfully() {

        // SIGNUP PROCESS
        Response createUserResponse = ReusableTest.signUp(0);
        // EXTRACT THE TOKEN FORM THE LOGIN RESPONSE BODY
        String token = createUserResponse.jsonPath().getString("token");

        // ADD CONTACT PROCESS
        Response addContactResponse = ReusableTest.addContact(token);
        // EXTRACT THE ID FROM ADD CONTACT RESPONSE BODY
        String id = addContactResponse.jsonPath().getString("_id");

        // PASS THE TOKEN, ID AND DELETE THE CONTACT
        Response response = ContactService.deleteContact(token, id);

        // ASSERTIONS
        AllureUtil.steps("Validate Successful delete contact");
        CommonAssertions.verifyStatusCode(response, 200);

    }
}