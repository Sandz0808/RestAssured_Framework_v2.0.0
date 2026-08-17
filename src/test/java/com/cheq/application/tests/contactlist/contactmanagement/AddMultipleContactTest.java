package com.cheq.application.tests.contactlist.contactmanagement;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.assertions.schema.SchemaAssertions;
import com.cheq.application.assertions.validation.ValidationAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.tests.contactlist.reusables.ReusableTest;
import com.cheq.application.utilities.AllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.application.listeners.RetryAnalyzer;
import org.testng.annotations.Test;


@Epic("Contact List API Testing")
@Feature("Add Contact Management")
public class AddMultipleContactTest extends Hooks {


    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "contact", "test"},
            description = "Validate Successful Add Multiple Contact"
    )
    public void testAddMultipleContactSuccessfully() {

        // SIGNUP PROCESS
        Response createUserResponse = ReusableTest.signUp(0);
        // EXTRACT THE TOKEN FORM THE LOGIN RESPONSE BODY
        String token = createUserResponse.jsonPath().getString("token");

       // LOOP THE ADD CONTACT PROCESS
        for (int i = 1; i <= 5; i++) {
            Response response = ReusableTest.addContact(token);

        // ASSERTIONS
        AllureUtil.steps("Validate Successful contact creation:     " + i + "");
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