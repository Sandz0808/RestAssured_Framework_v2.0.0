package com.cheq.contactlist.assertions.security;

import com.cheq.contactlist.utilities.LogSanitizerUtil;
import com.cheq.contactlist.utilities.LoggerUtil;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.Assert;

public class SecurityAssertions {

    private static final Logger log =
            LoggerUtil.getLogger(SecurityAssertions.class);


    public static void verifyPasswordNotExposed(Response response) {

        log.info("Verifying password is not exposed.");

        try {

            Allure.step("Verify Password Is Not Exposed", () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                String body = LogSanitizerUtil.sanitize(
                        response.getBody().asPrettyString());

                Allure.addAttachment(
                        "Sanitized Response",
                        "application/json",
                        body);

                Assert.assertFalse(
                        body.toLowerCase().contains("password"),
                        "Sensitive field 'password' was exposed.");

            });

            log.info("Password exposure verification passed.");

        } catch (AssertionError e) {

            log.error("Password exposure verification failed.", e);
            throw e;
        }
    }


    public static void verifyTokenNotExposed(Response response) {

        log.info("Verifying authentication token is not exposed.");

        try {

            Allure.step("Verify Authentication Token Is Not Exposed", () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                String body = LogSanitizerUtil.sanitize(
                        response.getBody().asPrettyString());

                Allure.addAttachment(
                        "Sanitized Response",
                        "application/json",
                        body);

                Assert.assertFalse(
                        body.contains("\"token\""),
                        "Authentication token was exposed.");

            });

            log.info("Authentication token exposure verification passed.");

        } catch (AssertionError e) {

            log.error("Authentication token exposure verification failed.", e);
            throw e;
        }
    }


    public static void verifyStackTraceNotExposed(Response response) {

        log.info("Verifying Java stacktrace is not exposed.");

        try {

            Allure.step("Verify Stacktrace Is Not Exposed", () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                String body = LogSanitizerUtil.sanitize(
                        response.getBody().asPrettyString());

                Allure.addAttachment(
                        "Sanitized Response",
                        "application/json",
                        body);

                Assert.assertFalse(
                        body.contains("Exception"),
                        "Java Exception exposed.");

                Assert.assertFalse(
                        body.contains("at "),
                        "Java Stacktrace exposed.");

            });

            log.info("Stacktrace exposure verification passed.");

        } catch (AssertionError e) {

            log.error("Stacktrace exposure verification failed.", e);
            throw e;
        }
    }


    public static void verifySqlErrorNotExposed(Response response) {

        log.info("Verifying SQL error information is not exposed.");

        try {

            Allure.step("Verify SQL Error Is Not Exposed", () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                String body = LogSanitizerUtil.sanitize(
                        response.getBody().asPrettyString());

                Allure.addAttachment(
                        "Sanitized Response",
                        "application/json",
                        body);

                Assert.assertFalse(
                        body.toLowerCase().contains("sql"),
                        "SQL information exposed.");

                Assert.assertFalse(
                        body.toLowerCase().contains("syntax error"),
                        "SQL syntax exposed.");

            });

            log.info("SQL error exposure verification passed.");

        } catch (AssertionError e) {

            log.error("SQL error exposure verification failed.", e);
            throw e;
        }
    }


    public static void verifyInternalPathNotExposed(Response response) {

        log.info("Verifying internal server paths are not exposed.");

        try {

            Allure.step("Verify Internal Server Paths Are Not Exposed", () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                String body = LogSanitizerUtil.sanitize(
                        response.getBody().asPrettyString());

                Allure.addAttachment(
                        "Sanitized Response",
                        "application/json",
                        body);

                Assert.assertFalse(
                        body.contains("/usr/"),
                        "Linux server path exposed.");

                Assert.assertFalse(
                        body.contains("C:\\"),
                        "Windows server path exposed.");

            });

            log.info("Internal server path exposure verification passed.");

        } catch (AssertionError e) {

            log.error("Internal server path exposure verification failed.", e);
            throw e;
        }
    }


    public static void verifySecurityHeaderExists(
            Response response,
            String headerName) {

        log.info("Verifying security header: {}", headerName);

        try {

            Allure.step("Verify Security Header: " + headerName, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                String value = response.getHeader(headerName);

                Allure.addAttachment(
                        "Security Header",
                        String.format(
                                "Header Name : %s%nHeader Value: %s",
                                headerName,
                                value));

                Assert.assertNotNull(
                        value,
                        "Security header '" + headerName + "' is missing.");

            });

            log.info("Security header '{}' verification passed.", headerName);

        } catch (AssertionError e) {

            log.error("Security header '{}' verification failed.", headerName, e);
            throw e;
        }
    }

}