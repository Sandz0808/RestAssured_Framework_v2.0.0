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
import static com.cheq.application.constants.contactlistconstant.ContactListHeaderConstant.*;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;
import static com.cheq.application.utilities.JsonReaderUtil.getDatalist;


@Epic("Contact List API Testing")
@Feature("Add Contact Management")
public class CreateContactTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"regression", "contact", "test"},
            description = "TC-Contact-003 - Validate Successful Add Contact"
    )
    public void testAddContactSuccessfully() {

        // GET THE FIRST NAME IN THE addContact.json
        String firstName = getDatalist("addContact",0, "firstName");

        // login Process
        Response signUpResponse = ReusableTest.signUp(0);
        // Extract the token from the login response body
        String token = signUpResponse.jsonPath().getString("token");

        // pass the Token and add contact process
        Response response = ReusableTest.addContact(token);

        // Assertions
        AllureUtil.steps("Validate Successful contact creation");
        CommonAssertions.verifyStatusCode(response, CREATED);
        CommonAssertions.verifyHeader(response, CONTENT_TYPE, CONTENT_TYPE_HEADER);
        CommonAssertions.verifyResponseTime(response, ALLOWED_RESPONSE_TIME);
        CommonAssertions.verifyBodyContainsText(response, firstName);
        CommonAssertions.verifyResponseBody(response);
        SchemaAssertions.verifySchema(response, SchemaAssertions.SchemaType.CREATE_CONTACT_SCHEMA);
        ValidationAssertions.verifyFieldEquals(response, "firstName", firstName);
        ValidationAssertions.verifyFieldExists(response, "email");

    }
}