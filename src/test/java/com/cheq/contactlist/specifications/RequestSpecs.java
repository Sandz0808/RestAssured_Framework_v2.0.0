package com.cheq.contactlist.specifications;

import com.cheq.contactlist.utilities.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

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