package com.cheq.application.tests.contactlist.usermanagement;

import com.cheq.application.assertions.authentication.AuthAssertions;
import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.assertions.schema.SchemaAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.utilities.AllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.models.contactlistmodel.userrequestmodel.CreateUser;
import org.testng.annotations.Test;
import com.cheq.application.payloads.contactlistpayload.users.CreateUserPayload;
import com.cheq.application.services.contactlistservice.UserService;
import static com.cheq.application.constants.contactlistconstant.ContactListHeaderConstant.*;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;
import static com.cheq.application.utilities.JsonReaderUtil.getDatalist;


@Epic("Contact List API Testing")
@Feature("Add User Management")
public class CreateUserTests extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"regression", "user", "test",},
            description = "TC-User-002 - Validate Successful user creation"
    )

    public void testSuccessfulUserSignup() {

        // GET THE FIRST NAME IN THE userSignup.json
        String firstName = getDatalist("userSignup",0, "firstName");

        CreateUser payload = CreateUserPayload.createValidUser(0);
        Response response = UserService.createUser(payload);


        AllureUtil.steps("Validate Successful user creation");
        CommonAssertions.verifyStatusCode(response, CREATED);
        CommonAssertions.verifyHeader(response, CONTENT_TYPE, CONTENT_TYPE_HEADER);
        CommonAssertions.verifyResponseTime(response, ALLOWED_RESPONSE_TIME);
        CommonAssertions.verifyBodyContainsText(response, firstName);
        CommonAssertions.verifyResponseBody(response);
        AuthAssertions.verifyAuthorizationToken(response.jsonPath().getString("token"));
        SchemaAssertions.verifySchema(response, SchemaAssertions.SchemaType.CREATE_USER_SCHEMA);

    }
}