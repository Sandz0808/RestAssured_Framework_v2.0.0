package com.cheq.contactlist.specs;

import com.cheq.contactlist.utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {


    /**
     * ==========================================================
     * DEFAULT REQUEST SPECIFICATION
     * ==========================================================
     */
    public static RequestSpecification getRequestSpec() {

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();

    }


    /**
     * ==========================================================
     * AUTHORIZATION (Bearer Token)
     * ==========================================================
     */
    public static RequestSpecification authRequestSpec(String token) {

        return new RequestSpecBuilder()
                .addRequestSpecification(getRequestSpec())
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

    /**
     * ==========================================================
     * API KEY
     * ==========================================================
     */
    public static RequestSpecification apiKeyRequestSpec() {

        return new RequestSpecBuilder()
                .addRequestSpecification(getRequestSpec())
                .addHeader(
                        "x-api-key",
                        ConfigReader.get("x.api.key"))
                .build();

    }

    /**
     * ==========================================================
     * AUTHORIZATION + API KEY
     * ==========================================================
     */
    public static RequestSpecification authApiKeyRequestSpec(
            String token) {

        return new RequestSpecBuilder()
                .addRequestSpecification(getRequestSpec())
                .addHeader(
                        "Authorization",
                        "Bearer " + token)
                .addHeader(
                        "x-api-key",
                        ConfigReader.get("x.api.key"))
                .build();

    }

}