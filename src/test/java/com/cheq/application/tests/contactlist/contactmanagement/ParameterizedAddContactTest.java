package com.cheq.application.tests.contactlist.contactmanagement;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.tests.contactlist.reusables.ReusableTest;
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
@Feature("Parameterized Add Contact")
public class ParameterizedAddContactTest extends Hooks {

    private static final Logger log =
            LoggerUtil.getLogger(ParameterizedAddContactTest.class);

    @DataProvider(name = "addContactTestData")
    public Object[][] addContactTestData() {

        return new Object[][]{ {0}, {1}, {2}, {3} };
    }

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"Regression", "contact", "test"},
            description = "TC-Contact-005 - Parameterized Add Contact That Covers Negative Scenarios",
            dataProvider = "addContactTestData"
    )
    public void testParameterizedAddContact(int index) {

        // PROCESS THE ITERATION OF ADD CONTACT TEST DATA - addContact.json
        Response response = ReusableTest.addContactParameterized(index);

        // EXTRACT THE EXPECTED RESULT CODE FROM THE TEST DATA TO BE USE IN ASSERTION
        int expectedStatusCode = Integer.parseInt(JsonReaderUtil.getDatalist("addContact", index, "expectedStatusCode"));

        // EXTRACT THE SCENARIO FROM THE TEST DATA TO BE USE IN ALLURE REPORT TAGGING
        String scenario = JsonReaderUtil.getDatalist("addContact", index, "scenario");

        // ALLURE FEATURE TO REFLECT THE ITERATED RUN IN THE ALLURE REPORT ONE BY ONE
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(scenario));

        // PUT LOGGER FOR EASY IDENTIFY THE RUN IN THE LOGS AND EASY DEBUGGING
        log.info("=========== {} ===========", scenario);

        // COMMON ASSERTIONS
        AllureUtil.steps("Parameterized Add Contact That Covers Negative Scenarios");
        CommonAssertions.verifyStatusCode( response, expectedStatusCode);
        CommonAssertions.verifyResponseTime(response, ALLOWED_RESPONSE_TIME);
    }
}