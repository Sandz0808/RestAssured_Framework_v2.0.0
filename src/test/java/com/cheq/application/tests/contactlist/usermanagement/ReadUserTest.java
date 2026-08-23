package com.cheq.application.tests.contactlist.usermanagement;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.payloads.contactlistpayload.users.CreateUserPayload;
import com.cheq.application.utilities.AllureUtil;
import io.qameta.allure.*;
import io.restassured.response.Response;
import com.cheq.application.listeners.RetryAnalyzer;
import org.testng.annotations.Test;
import com.cheq.application.services.contactlistservice.UserService;
import static com.cheq.application.constants.contactlistconstant.ContactListHeaderConstant.*;
import static com.cheq.application.constants.statuscode.StatusCodeConstant.*;
import static com.cheq.application.utilities.JsonReaderUtil.getDatalist;


@Epic("Contact List API Testing")
@Feature("Add User Management")
public class ReadUserTest extends Hooks {

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "user", "test"},
            description = "TC-User-005 - Validate Successful read user"
    )
    public void testReadUser() {

        // GET THE FIRST NAME IN THE userSignup.json
        String firstName = getDatalist("userSignup",0, "firstName");

        Response createUserResponse = UserService.createUser(CreateUserPayload.createValidUser(0));
        String dynamicToken = createUserResponse.jsonPath().getString("token");

        Response response = UserService.readUser(dynamicToken);

        AllureUtil.steps("Validate Successful read user");
        CommonAssertions.verifyStatusCode(response, OK);
        CommonAssertions.verifyHeader(response, CONTENT_TYPE, CONTENT_TYPE_HEADER);
        CommonAssertions.verifyResponseTime(response, ALLOWED_RESPONSE_TIME);
        CommonAssertions.verifyBodyContainsText(response, firstName);
        CommonAssertions.verifyResponseBody(response);
        CommonAssertions.verifyResponseHeaders(response);



    }
}