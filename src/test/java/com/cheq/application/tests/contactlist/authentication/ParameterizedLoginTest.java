package com.cheq.application.tests.contactlist.authentication;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.models.contactlistmodel.userrequestmodel.LoginRequest;
import com.cheq.application.payloads.contactlistpayload.authenticaion.LoginPayload;
import com.cheq.application.services.contactlistservice.AuthenticationService;
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
            description = "TC-Auth-004 - Parameterized Login That Covers Negative Scenarios",
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

        AllureUtil.steps("Parameterized Login That Covers Negative Scenarios");
        CommonAssertions.verifyStatusCode(response, expectedStatusCode);
        CommonAssertions.verifyResponseTime(response, ALLOWED_RESPONSE_TIME);

    }
}