package com.cheq.contactlist.tests.authentication;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import com.cheq.contactlist.models.userrequestmodel.LoginRequest;
import com.cheq.contactlist.payloads.authenticaion.*;
import com.cheq.contactlist.services.AuthenticationService;
import com.cheq.contactlist.utilities.JsonReaderUtil;
import com.cheq.contactlist.utilities.LoggerUtil;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


@Epic("Contact List API Testing")
@Feature("Parameterized Login")
public class ParameterizedLoginTest extends Hooks {

    private static final Logger log =
            LoggerUtil.getLogger(AuthenticationService.class);

    @DataProvider(name = "loginTestData")
    public Object[][] loginTestData() {

        return new Object[][]{ {0}, {1}, {2}, {3}, {4}};
    }

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"smoke", "auth", "test"},
            description = "Parameterized Login That Covers Negative Scenarios",
            dataProvider = "loginTestData"
    )
    public void testParameterizedLogin(int index) {

        LoginRequest payload = LoginPayload.createLogin(index);

        Response response = AuthenticationService.loginUser(payload);

        int expectedStatusCode = Integer.parseInt(
                JsonReaderUtil.getDatalist("userLogin", index, "expectedStatusCode"));

        String scenario = JsonReaderUtil.getDatalist("userSignup", index, "scenario");

        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(scenario));

        log.info("=========== {} ===========", scenario);

        CommonAssertions.verifyStatusCode(response, expectedStatusCode);
        CommonAssertions.verifyResponseTime(response, 2000);

    }
}