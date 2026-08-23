package com.cheq.application.tests.contactlist.contactmanagement;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.assertions.validation.ValidationAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.tests.contactlist.reusables.ReusableTest;
import com.cheq.application.utilities.AllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.application.listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import com.cheq.application.services.contactlistservice.ContactService;
import static com.cheq.application.constants.contactlistconstant.ContactListHeaderConstant.*;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;
import static com.cheq.application.utilities.JsonReaderUtil.getDatalist;


@Epic("Contact List API Testing")
@Feature("Add Contact Management")
public class ReadContactTest extends Hooks {


    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "contact", "test"},
            description = "TC-Contact-006 - Validate Successful Read Contact"
    )
    public void testReadContactSuccessfully() {

        // GET THE FIRST NAME IN THE ADD CONTACT addContact.json
        String firstName = getDatalist("addContact",0, "firstName");

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
        CommonAssertions.verifyStatusCode(response, OK);
        CommonAssertions.verifyHeader(response, CONTENT_TYPE, CONTENT_TYPE_HEADER);
        CommonAssertions.verifyResponseTime(response, ALLOWED_RESPONSE_TIME);
        CommonAssertions.verifyBodyContainsText(response, firstName);
        CommonAssertions.verifyResponseBody(response);
        ValidationAssertions.verifyFieldEquals(response, "firstName", firstName);
        ValidationAssertions.verifyFieldExists(response, "email");



    }
}