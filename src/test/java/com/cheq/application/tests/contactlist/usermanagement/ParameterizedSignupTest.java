package com.cheq.application.tests.contactlist.usermanagement;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.models.contactlistmodel.userrequestmodel.CreateUser;
import com.cheq.application.payloads.contactlistpayload.users.CreateUserPayload;
import com.cheq.application.services.contactlistservice.AuthenticationService;
import com.cheq.application.services.contactlistservice.UserService;
import com.cheq.application.utilities.AllureUtil;
import com.cheq.application.utilities.JsonReaderUtil;
import com.cheq.application.utilities.LoggerUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static com.cheq.application.constants.contactlistconstant.ContactListHeaderConstant.*;


@Epic("Contact List API Testing")
@Feature("Parameterized Add User")

public class ParameterizedSignupTest extends Hooks {

    private static final Logger log =
            LoggerUtil.getLogger(AuthenticationService.class);

    @DataProvider(name = "createUserTestData")
    public Object[][] createUserTestData() {

        return new Object[][]{ {0}, {2},  {3}, {5}, {6} };

    }

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"sanity", "user", "test"},
            description = "TC-User-004 - Parameterized User Signup That Covers Negative Scenarios",
            dataProvider = "createUserTestData"
    )
    public void testParameterizedUserSignup(int index) {

        CreateUser payload = CreateUserPayload.createValidUser(index);

        Response response = UserService.createUser(payload);

        int expectedStatusCode = Integer.parseInt(
                JsonReaderUtil.getDatalist("userSignup", index, "expectedStatusCode"));

        String scenario = JsonReaderUtil.getDatalist("userSignup", index, "scenario");

        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(scenario));

        log.info("=========== {} ===========", scenario);

        AllureUtil.steps("Parameterized User Signup That Covers Negative Scenarios");
        CommonAssertions.verifyStatusCode(response, expectedStatusCode);
        CommonAssertions.verifyResponseTime( response, ALLOWED_RESPONSE_TIME);

    }
}

