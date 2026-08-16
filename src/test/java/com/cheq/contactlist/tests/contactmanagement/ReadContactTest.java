package com.cheq.contactlist.tests.contactmanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.assertions.validation.ValidationAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.tests.reusables.ReusableTest;
import com.cheq.contactlist.utilities.AllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import com.cheq.contactlist.services.ContactService;


@Epic("Contact List API Testing")
@Feature("Add Contact Management")
public class ReadContactTest extends Hooks {


    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "contact", "test"},
            description = "Validate Successful Read Contact"
    )
    public void testReadContactSuccessfully() {

        // LOGIN PROCESS
        Response signUpResponse = ReusableTest.signUp(0);
        // EXTRACT THE TOKEN FORM THE LOGIN RESPONSE BODY
        String token = signUpResponse.jsonPath().getString("token");

        // ADD CONTACT PROCESS
        Response addContactResponse = ReusableTest.addContact(token);
        // EXTRACT THE ID FROM ADD CONTACT RESPONSE BODY
        String dynamicContactId = addContactResponse.jsonPath().getString("_id");

        // PASS THE EXTRACTED TOKEN AND ID TO RETRIEVE THE CONTACT DETAILS
        Response response = ContactService.getContact(token, dynamicContactId);

        // ASSERTIONS
        AllureUtil.steps("Validate Successful read contact");
        CommonAssertions.verifyStatusCode(response, 200);
        CommonAssertions.verifyHeader(response, "Content-Type", "application/json; charset=utf-8");
        CommonAssertions.verifyResponseTime(response, 2000);
        CommonAssertions.verifyBodyContainsText(response, "John");
        CommonAssertions.verifyResponseBody(response);
        ValidationAssertions.verifyFieldEquals(response, "firstName", "John");
        ValidationAssertions.verifyFieldExists(response, "email");



    }
}