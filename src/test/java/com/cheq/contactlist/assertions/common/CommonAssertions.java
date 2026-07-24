package com.cheq.contactlist.assertions.common;

import com.cheq.contactlist.utils.LogSanitizerUtil;
import com.cheq.contactlist.utils.LoggerUtil;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.Assert;

public class CommonAssertions {

    private static final Logger log =
            LoggerUtil.getLogger(CommonAssertions.class);

    public static void verifyStatusCode(Response response, int expectedStatusCode) {

        log.info("Verifying Status Code. Expected: {}", expectedStatusCode);

        try {

            Allure.step("Verify Status Code", () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                int actualStatusCode = response.statusCode();

                Allure.addAttachment(
                        "Status Code",
                        String.format(
                                "Expected Status Code : %d%nActual Status Code   : %d",
                                expectedStatusCode,
                                actualStatusCode));

                Assert.assertEquals(
                        actualStatusCode,
                        expectedStatusCode,
                        "Status Code mismatch.");

            });

            log.info("Status Code verification passed.");

        } catch (AssertionError e) {

            log.error("Status Code verification failed.", e);
            throw e;
        }
    }


    public static void verifyHeader(Response response,
                                    String headerName,
                                    String expectedValue) {

        log.info("Verifying Header: {}", headerName);

        try {

            Allure.step("Verify Header: " + headerName, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                String actualValue = response.getHeader(headerName);

                Assert.assertNotNull(
                        actualValue,
                        "Header '" + headerName + "' was not found.");

                Allure.addAttachment(
                        "Header Verification",
                        String.format(
                                "Header Name : %s%nExpected    : %s%nActual      : %s",
                                headerName,
                                expectedValue,
                                actualValue));

                Assert.assertEquals(
                        actualValue,
                        expectedValue,
                        "Header value mismatch.");

            });

            log.info("Header '{}' verification passed.", headerName);

        } catch (AssertionError e) {

            log.error("Header '{}' verification failed.", headerName, e);
            throw e;
        }
    }

    public static void verifyResponseBody(Response response) {

        log.info("Verifying Response Body.");

        try {

            Allure.step("Verify Response Body", () -> {

                Assert.assertNotNull(
                        response,
                        "Response object should not be null.");

                String body = LogSanitizerUtil.sanitize(
                        response.getBody().asPrettyString());

                Assert.assertFalse(
                        body.trim().isEmpty(),
                        "Response body should not be empty.");

                Allure.addAttachment(
                        "Response Body",
                        "application/json",
                        body);

            });

            log.info("Response Body verification passed.");

        } catch (AssertionError e) {

            log.error("Response Body verification failed.", e);
            throw e;
        }
    }


    public static void verifyBodyContainsText(
            Response response,
            String expectedText) {

        log.info("Verifying Response Body contains text: {}", expectedText);

        try {

            Allure.step("Verify Response Contains Text", () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                String responseBody = LogSanitizerUtil.sanitize(
                        response.getBody().asPrettyString());

                Assert.assertTrue(
                        responseBody.contains(expectedText),
                        String.format(
                                "Response body does not contain the expected text.%nExpected: %s",
                                expectedText));

                Allure.addAttachment(
                        "Expected Text",
                        expectedText);

                Allure.addAttachment(
                        "Actual Text",
                        expectedText);

            });

            log.info("Response Body text verification passed.");

        } catch (AssertionError e) {

            log.error("Response Body text verification failed.", e);
            throw e;
        }
    }


    public static void verifyResponseTime(
            Response response,
            long expectedMaxResponseTime) {

        log.info("Verifying Response Time. Threshold: {} ms", expectedMaxResponseTime);

        try {

            Allure.step("Verify Response Time", () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                long actualResponseTime = response.getTime();

                Allure.addAttachment(
                        "Response Time Verification",
                        String.format(
                                "Expected Maximum : %d ms%nActual Response  : %d ms",
                                expectedMaxResponseTime,
                                actualResponseTime));

                Assert.assertTrue(
                        actualResponseTime <= expectedMaxResponseTime,
                        String.format(
                                "Response Time exceeded threshold! Expected <= %d ms but was %d ms.",
                                expectedMaxResponseTime,
                                actualResponseTime));

            });

            log.info("Response Time verification passed.");

        } catch (AssertionError e) {

            log.error("Response Time verification failed.", e);
            throw e;
        }
    }

}