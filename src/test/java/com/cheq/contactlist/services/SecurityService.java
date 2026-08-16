package com.cheq.contactlist.services;

import com.cheq.contactlist.specifications.RequestSpecs;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import org.slf4j.Logger;
import javax.net.ssl.SSLException;
import com.cheq.contactlist.utilities.LoggerUtil;
import static io.restassured.RestAssured.given;
import javax.net.ssl.SSLContext;
import java.security.SecureRandom;


public final class SecurityService {

    private static final Logger log =
            LoggerUtil.getLogger(SecurityService.class);

    private SecurityService() {
    }


    /**
     * ==========================================================
     * HTTP ENFORCEMENT
     * Sends an unsecured HTTP request without following redirects.
     * ==========================================================
     */
    public static Response getHttpWithoutRedirect(String endpoint) {

        log.info("========== HTTP ENFORCEMENT ==========");
        log.info("Endpoint : {}", endpoint);
        log.info("Method   : GET");

        Response response = given()
                .spec(RequestSpecs.requestSpec())
                .redirects()
                .follow(false)
                .when()
                .get(endpoint);

        log.info("Status Code  : {}", response.statusCode());
        log.info("Response Time: {} ms", response.time());

        return response;
    }


    /**
     * ==========================================================
     * TLS 1.0
     * Attempts connection using TLS 1.0.
     * ==========================================================
     */
    public static Response getWithTls10(String endpoint)
            throws SSLException {

        log.info("========== TLS 1.0 SECURITY TEST ==========");
        log.info("Endpoint  : {}", endpoint);
        log.info("Protocol : TLSv1");
        log.info("Method : GET");

        RestAssuredConfig tls10Config =
                createTlsConfig("TLSv1");

        Response response = given()
                .config(tls10Config)
                .spec(RequestSpecs.requestSpec())
                .when()
                .get(endpoint);

        log.info("Status Code : {}", response.statusCode());
        log.info("Response Time : {} ms", response.time());

        return response;
    }


    /**
     * ==========================================================
     * TLS 1.2
     * Attempts connection using TLS 1.2.
     * ==========================================================
     */
    public static Response getWithTls12(String endpoint) {

        log.info("========== TLS 1.2 SECURITY TEST ==========");
        log.info("Endpoint: {}", endpoint);
        log.info("Protocol : TLSv1.2");
        log.info("Method  : GET");

        RestAssuredConfig tls12Config =
                createTlsConfig("TLSv1.2");

        Response response = given()
                .config(tls12Config)
                .spec(RequestSpecs.requestSpec())
                .when()
                .get(endpoint);

        log.info("Status Code: {}", response.statusCode());
        log.info("Response Time:{} ms", response.time());

        return response;
    }

    private static RestAssuredConfig createTlsConfig(String protocol) {

        try {

            SSLContext sslContext =
                    SSLContext.getInstance(protocol);

            sslContext.init(
                    null,
                    null,
                    new SecureRandom()
            );

            org.apache.http.conn.ssl.SSLSocketFactory socketFactory =
                    new org.apache.http.conn.ssl.SSLSocketFactory(
                            sslContext
                    );

            return RestAssured.config()
                    .sslConfig(
                            SSLConfig.sslConfig()
                                    .sslSocketFactory(socketFactory)
                    );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to create TLS configuration for: " + protocol,
                    e
            );
        }
    }
}