package com.cheq.application.tests.contactlist.contactmanagement;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.tests.contactlist.reusables.ReusableTest;
import com.cheq.application.utilities.AllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.models.contactlistmodel.contactrequestmodel.UpdateContact;
import org.testng.annotations.Test;
import com.cheq.application.payloads.contactlistpayload.contacts.UpdateContactPayload;
import com.cheq.application.services.contactlistservice.ContactService;
import static com.cheq.application.constants.contactlistconstant.ContactListHeaderConstant.*;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;
import static com.cheq.application.utilities.JsonReaderUtil.getData;


@Epic("Contact List API Testing")
@Feature("Add Contact Management")
public class UpdateContactTest extends Hooks {


    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"regression", "contact", "test"},
            description = "TC-Contact-007 - Validate Successful Update Contact"
    )
    public void testUpdateContactSuccessfully() {

        // GET THE FIRST NAME IN THE addContact.json
        String updatedFirstName = getData("updateContact", "firstName");

        // SIGNUP PROCESS
        Response createUserResponse = ReusableTest.signUp(0);
        // EXTRACT THE TOKEN FORM THE LOGIN RESPONSE BODY
        String token = createUserResponse.jsonPath().getString("token");

        // ADD CONTACT PROCESS
        Response addContactResponse = ReusableTest.addContact(token);
        // EXTRACT THE ID FROM ADD CONTACT RESPONSE BODY
        String id = addContactResponse.jsonPath().getString("_id");

        // GET THE UPDATE CONTACT PAYLOAD
        UpdateContact payload = UpdateContactPayload.updateValidContact();
        // PASS THE TOKEN, ID AND PAYLOAD TO UPDATE THE CONTACT
        Response response = ContactService.updateContact(token, id, payload);

        // ASSERTIONS
        AllureUtil.steps("Validate Successful update contact");
        CommonAssertions.verifyStatusCode(response, OK);
        CommonAssertions.verifyHeader(response,  CONTENT_TYPE, CONTENT_TYPE_HEADER);
        CommonAssertions.verifyResponseTime(response, ALLOWED_RESPONSE_TIME);
        CommonAssertions.verifyBodyContainsText(response, updatedFirstName);
        CommonAssertions.verifyResponseBody(response);




    }
}