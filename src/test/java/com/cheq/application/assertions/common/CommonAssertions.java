package com.cheq.application.assertions.common;

import com.cheq.application.utilities.LogSanitizerUtil;
import com.cheq.application.utilities.LoggerUtil;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.Assert;
import io.restassured.http.Headers;

public class CommonAssertions {

    private static final Logger log =
            LoggerUtil.getLogger(CommonAssertions.class);

    public static void verifyStatusCode(Response response, int expectedStatusCode) {

        log.info("Verifying Success Status Code. Expected: {}", expectedStatusCode);

        try {

            Allure.step("Verify Status Code: " + expectedStatusCode, () -> {

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

            Allure.step("Verify Response Contains Text: " + expectedText, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                String responseBody = LogSanitizerUtil.sanitize(
                        response.getBody().asPrettyString());

                Assert.assertTrue(
                        responseBody.contains(expectedText),
                        String.format(
                                "Response body does not contain expected text '%s'.",
                                expectedText));

                // Extract the actual matched text
                String actualText = responseBody.contains(expectedText)
                        ? expectedText
                        : "NOT FOUND";

                Allure.addAttachment(
                        "Response Text Verification",
                        "text/plain",
                        String.format(
                                """
                                Expected Text : %s
                                Actual Text   : %s
                                """,
                                expectedText,
                                actualText));

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

            Allure.step("Verify Response Time ≤: " + expectedMaxResponseTime, () -> {

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

    public static void verifyResponseHeaders(Response response) {

        log.info("Verifying Response Headers.");

        try {

            Allure.step("Verify Response Headers", () -> {

                // Verify response object exists
                Assert.assertNotNull(
                        response,
                        "Response object should not be null.");

                // Get all response headers
                Headers headers = response.headers();

                // Verify headers are present
                Assert.assertTrue(
                        headers.size() > 0,
                        "Response headers should not be empty.");

                // Sanitize sensitive header values
                String sanitizedHeaders =
                        LogSanitizerUtil.sanitizeHeaders(headers);

                // Attach sanitized headers to Allure
                Allure.addAttachment(
                        "Response Headers",
                        "text/plain",
                        sanitizedHeaders);

            });

            log.info("Response Headers verification passed.");

        } catch (AssertionError e) {

            log.error("Response Headers verification failed.", e);
            throw e;

        }

    }

}