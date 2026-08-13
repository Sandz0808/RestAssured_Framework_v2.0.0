package com.cheq.contactlist.tests.usermanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import com.cheq.contactlist.models.userrequestmodel.CreateUser;
import com.cheq.contactlist.payloads.users.CreateUserPayload;
import com.cheq.contactlist.services.AuthService;
import com.cheq.contactlist.services.UserService;
import com.cheq.contactlist.utilities.JsonReaderUtil;
import com.cheq.contactlist.utilities.LoggerUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.Console;


@Epic("Contact List API Testing")
@Feature("Parameterized Add User")

public class TC013ParameterizedSignupTest extends Hooks {

    private static final Logger log =
            LoggerUtil.getLogger(AuthService.class);

    @DataProvider(name = "createUserTestData")
    public Object[][] createUserTestData() {

        return new Object[][]{ {0}, {2},  {3}, {5}, {6} };

    }

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"sanity", "user", "test"},
            description = "TC013-Parameterized User Signup That Covers Negative Scenarios",
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

        CommonAssertions.verifyStatusCode(response, expectedStatusCode);
        CommonAssertions.verifyResponseTime( response, 2000);

    }
}

