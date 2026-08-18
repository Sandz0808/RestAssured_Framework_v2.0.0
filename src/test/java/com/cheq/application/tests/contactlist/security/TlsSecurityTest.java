package com.cheq.application.tests.contactlist.security;

import com.cheq.application.assertions.common.CommonAssertions;
import com.cheq.application.hooks.Hooks;
import com.cheq.application.listeners.RetryAnalyzer;
import com.cheq.application.services.contactlistservice.TlsSecurityService;
import com.cheq.application.utilities.ConfigReader;
import com.cheq.application.utilities.LoggerUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.net.ssl.SSLException;
@Epic("Contact List API Testing")
@Feature("Security Management")
public class TlsSecurityTest extends Hooks {

    private static final Logger log =
            LoggerUtil.getLogger(TlsSecurityTest.class);

    private static final String HTTPS_ENDPOINT = ConfigReader.get("base.url.contactlist");
    private static final String HTTP_ENDPOINT = HTTPS_ENDPOINT.replace("https://", "http://");

    /**
     * TC - Verify HTTP is redirected to HTTPS
     */
    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"security", "tls", "test"},
            description = "Verify HTTP requests are redirected to HTTPS"
    )
    public void testHttpEnforcement() {

        Response response =
                TlsSecurityService.getHttpWithoutRedirect(
                        HTTP_ENDPOINT
                );

        if (response.statusCode() == 200) {

            Assert.fail(
                    "API does not support redirect. "
                            + "HTTP request returned 200 instead of redirecting to HTTPS."
            );
        }

        CommonAssertions.verifyStatusCode(
                response,
                response.statusCode() == 301
                        || response.statusCode() == 308
                        ? response.statusCode()
                        : 301
        );

        Assert.assertTrue(
                response.statusCode() == 301
                        || response.statusCode() == 308,
                "HTTP request should be redirected to HTTPS. "
                        + "Actual status: " + response.statusCode()
        );
    }


    /**
     * TC - Verify TLS 1.0 is rejected
     */
    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"security", "tls", "test"},
            description = "Verify TLS 1.0 connections are rejected"
    )
    public void testTls10IsRejected() {

        try {

            TlsSecurityService.getWithTls10(
                    HTTPS_ENDPOINT
            );

            Assert.fail(
                    "Security vulnerability: "
                            + "API accepted TLS 1.0 connection."
            );

        } catch (SSLException e) {

            log.info(
                    "TLS 1.0 connection rejected as expected: {}",
                    e.getMessage()
            );
        }
    }


    /**
     * TC - Verify TLS 1.2 is accepted
     */
    @Test(
            retryAnalyzer = RetryAnalyzer.class,
            groups = {"security", "tls", "test"},
            description = "Verify TLS 1.2 connections are accepted"
    )
    public void testTls12IsAccepted() {

        Response response =
                TlsSecurityService.getWithTls12(
                        HTTPS_ENDPOINT
                );

        CommonAssertions.verifyStatusCode(
                response,
                200
        );

        CommonAssertions.verifyResponseTime(
                response,
                2000
        );
    }
}