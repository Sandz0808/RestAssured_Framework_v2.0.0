package com.cheq.application.specifications;

import com.cheq.application.utilities.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

    private static final ThreadLocal<String> APPLICATION =
            new ThreadLocal<>();

    /**
     * Set application for the current test thread.
     */
    public static void setApplication(String application) {
        APPLICATION.set(application);
    }

    /**
     * Get application for the current test thread.
     */
    public static String getApplication() {
        return APPLICATION.get();
    }

    /**
     * Clear application after test execution.
     */
    public static void clearApplication() {
        APPLICATION.remove();
    }

    /**
     * Get Base URL based on current application.
     */
    private static String getBaseUrl() {

        String application = APPLICATION.get();

        if (application == null || application.isBlank()) {
            throw new IllegalStateException(
                    "Application is not configured for the current test thread.");
        }

        String baseUrlKey = "base.url." + application;

        return ConfigReader.get(baseUrlKey);
    }

    /**
     * ==========================================================
     * DEFAULT REQUEST SPECIFICATION
     * No Token / No API Key
     * ==========================================================
     */
    public static RequestSpecification requestSpec() {

        return requestSpec(null, null);
    }

    /**
     * ==========================================================
     * REQUEST SPECIFICATION WITH BEARER TOKEN
     * Token only
     * ==========================================================
     */
    public static RequestSpecification requestSpec(String token) {

        return requestSpec(token, null);
    }

    /**
     * ==========================================================
     * REQUEST SPECIFICATION WITH TOKEN + API KEY
     * ==========================================================
     */
    public static RequestSpecification requestSpec(
            String token,
            String apiKey) {

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(getBaseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);

        if (token != null && !token.isBlank()) {
            builder.addHeader(
                    "Authorization",
                    "Bearer " + token);
        }

        if (apiKey != null && !apiKey.isBlank()) {
            builder.addHeader(
                    "x-api-key",
                    apiKey);
        }

        return builder.build();
    }

    /**
     * ==========================================================
     * REQUEST SPECIFICATION WITH CONFIGURED API KEY
     * ==========================================================
     */
    public static RequestSpecification requestSpecWithApiKey() {

        return requestSpec(
                null,
                ConfigReader.get("x.api.key"));
    }
}