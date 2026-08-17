package com.cheq.application.assertions.authentication;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.utilities.LoggerUtil;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.Assert;


public class AuthAssertions {

    private static final Logger log =
            LoggerUtil.getLogger(AuthAssertions.class);


    public static void verifyAuthorizationToken(String token) {

        log.info("Verifying Authorization Token.");

        Allure.step("Verify Authorization Token", () -> {

            String sanitizedToken =
                    (token == null || token.isBlank())
                            ? "{}"
                            : "******";

            Allure.addAttachment(
                    "Authorization Token",
                    sanitizedToken);

            Assert.assertNotNull(
                    token,
                    "Authorization token is null.");

            Assert.assertFalse(
                    token.trim().isEmpty(),
                    "Authorization token is empty.");

        });

    }


    public static void verifyTokenExists(Response response) {

        log.info("Verifying Authentication Token Exists.");

        try {

            Allure.step("Verify Authentication Token Exists", () -> {

                String token = response.jsonPath().getString("token");

                String sanitizedToken = (token == null || token.isBlank())
                        ? "{}"
                        : "Token*******";

                Allure.addAttachment(
                        "Authentication Token",
                        sanitizedToken);

                Assert.assertNotNull(
                        token,
                        "Authentication token is null.");

                Assert.assertFalse(
                        token.trim().isEmpty(),
                        "Authentication token is empty.");
            });

            log.info("Authentication Token verification passed.");

        } catch (AssertionError e) {

            log.error("Authentication Token verification failed.", e);
            throw e;
        }
    }



    public static void verifyTokenDoesNotExist(Response response) {

        log.info("Verifying Authentication Token Does Not Exist.");

        try {

            Allure.step("Verify Authentication Token Does Not Exist", () -> {

                String token = response.jsonPath().getString("token");

                String sanitizedToken = (token == null || token.isBlank())
                        ? "{}"
                        : "Token******";

                Allure.addAttachment(
                        "Authentication Token",
                        sanitizedToken);

                Assert.assertTrue(
                        token == null || token.trim().isEmpty(),
                        "Authentication token should not exist.");
            });

            log.info("Authentication Token absence verified successfully.");

        } catch (AssertionError e) {

            log.error("Authentication Token absence verification failed.", e);
            throw e;
        }
    }



    public static void verifyForbidden(Response response) {

        log.info("Verifying HTTP Status Code: 403 Forbidden.");

        try {

            Allure.step("Verify HTTP Status Code: 403 Forbidden", () ->
                    CommonAssertions.verifyStatusCode(response, 403));

            log.info("HTTP 403 Forbidden verification passed.");

        } catch (AssertionError e) {

            log.error("HTTP 403 Forbidden verification failed.", e);
            throw e;
        }
    }



    public static void verifyUnauthorized(Response response) {

        log.info("Verifying HTTP Status Code: 401 Unauthorized.");

        try {

            Allure.step("Verify HTTP Status Code: 401 Unauthorized", () ->
                    CommonAssertions.verifyStatusCode(response, 401));

            log.info("HTTP 401 Unauthorized verification passed.");

        } catch (AssertionError e) {

            log.error("HTTP 401 Unauthorized verification failed.", e);
            throw e;
        }
    }


    public static void verifyJwtFormat(Response response) {

        log.info("Verifying JWT Token Format.");

        try {

            Allure.step("Verify JWT Token Format", () -> {

                String token = response.jsonPath().getString("token");

                String sanitizedToken = (token == null || token.isBlank())
                        ? "{}"
                        : "🙈🙃🛡️✨👻🥷";

                Allure.addAttachment(
                        "JWT Token",
                        sanitizedToken);

                Assert.assertNotNull(
                        token,
                        "JWT token is null.");

                Assert.assertFalse(
                        token.trim().isEmpty(),
                        "JWT token is empty.");

                String[] jwtParts = token.split("\\.");

                Allure.addAttachment(
                        "JWT Structure",
                        String.format(
                                "Expected Parts : 3%nActual Parts   : %d",
                                jwtParts.length));

                Assert.assertEquals(
                        jwtParts.length,
                        3,
                        "Invalid JWT format.");
            });

            log.info("JWT Token format verification passed.");

        } catch (AssertionError e) {

            log.error("JWT Token format verification failed.", e);
            throw e;
        }
    }

}