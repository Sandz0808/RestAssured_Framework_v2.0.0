package com.cheq.application.tests.contactlist.contactmanagement;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.tests.contactlist.reusables.ReusableTest;
import com.cheq.application.utilities.AllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import com.cheq.application.services.contactlistservice.ContactService;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;


@Epic("Contact List API Testing")
@Feature("Add Contact Management")
public class DeleteContactTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "contact", "test"},
            description = "TC-Contact-004 - Validate Successful Delete Contact"
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
        CommonAssertions.verifyStatusCode(response, OK);

    }
}