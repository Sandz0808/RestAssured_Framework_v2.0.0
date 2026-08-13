package com.cheq.contactlist.tests.contactmanagement;

import com.cheq.contactlist.assertions.common.CommonAssertions;
import com.cheq.contactlist.hooks.Hooks;
import com.cheq.contactlist.listeners.RetryAnalyzer;
import com.cheq.contactlist.models.contactrequestmodel.CreateContact;
import com.cheq.contactlist.payloads.contacts.AddContactPayload;
import com.cheq.contactlist.payloads.users.CreateUserPayload;
import com.cheq.contactlist.services.ContactService;
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

@Epic("Contact List API Testing")
@Feature("Parameterized Add Contact")
public class TC014ParameterizedAddContactTest extends Hooks {

    private static final Logger log =
            LoggerUtil.getLogger(TC014ParameterizedAddContactTest.class);


    @DataProvider(name = "addContactTestData")
    public Object[][] addContactTestData() {

        return new Object[][]{ {0}, {1}, {2}, {3} };
    }

    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"sanity", "contact", "test"},
            description = "TC014-Parameterized Add Contact That Covers Negative Scenarios",
            dataProvider = "addContactTestData"
    )
    public void testParameterizedAddContact(int index) {

        Response createUserResponse = UserService.createUser(CreateUserPayload.createValidUser(0));
        String dynamicToken = createUserResponse.jsonPath().getString("token");

        CreateContact payload = AddContactPayload.createValidContact(index);
        Response response = ContactService.addContact(dynamicToken, payload);

        int expectedStatusCode = Integer.parseInt(JsonReaderUtil.getDatalist("addContact", index, "expectedStatusCode"));

        String scenario = JsonReaderUtil.getDatalist("addContact", index, "scenario");

        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(scenario));


        log.info("=========== {} ===========", scenario);

        CommonAssertions.verifyStatusCode( response, expectedStatusCode);
        CommonAssertions.verifyResponseTime(response, 2000);
    }
}