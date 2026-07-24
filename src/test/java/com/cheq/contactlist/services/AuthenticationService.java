package com.cheq.contactlist.services;

import io.restassured.response.Response;
import com.cheq.contactlist.models.userrequestmodel.LoginRequest;
import org.slf4j.Logger;
import com.cheq.contactlist.utils.ApiAllureUtil;
import com.cheq.contactlist.utils.LoggerUtil;
import com.cheq.contactlist.utils.SaveResponseUtil;
import static com.cheq.contactlist.constants.EndpointConstant.*;
import static com.cheq.contactlist.specs.RequestSpec.*;
import static io.restassured.RestAssured.given;

public class AuthenticationService {

    private static final Logger log =
            LoggerUtil.getLogger(AuthenticationService.class);

    // =================================================================
    // LOGIN: Authenticate an existing user (Public Endpoint)
    // =================================================================
    public static Response login(LoginRequest payload) {

        log.info("========================================");
        log.info("========== USER LOGIN ==========");
        log.info("Endpoint : {}", LOGIN_USER);
        log.info("Method   : POST");
        log.info("Email    : {}", payload.getEmail());

        ApiAllureUtil.steps(
                "Send POST /login request to authenticate user");

        ApiAllureUtil.attachEndpoint(LOGIN_USER);
        ApiAllureUtil.attachRequest(payload);

        Response response = given()
                .spec(getRequestSpec())
                .body(payload)
                .when()
                .post(LOGIN_USER);

        log.info("Status Code   : {}", response.statusCode());
        log.info("Response Time : {} ms", response.time());

        ApiAllureUtil.attachResponse(response);

        SaveResponseUtil.saveResponse(
                response,
                "LoginResponse");

        log.info("Response saved to LoginResponse.json");

        return response;
    }

    public static Response logout(String token) {

        log.info(" ========================================");
        log.info("========== USER LOGOUT ==========");
        log.info(" Endpoint : {}", LOGOUT_USER);
        log.info("Method   :  POST");

        ApiAllureUtil.steps(
                "Send POST /logout request to invalidate user session");

        Response response = given()
                .spec(authRequestSpec(token))
                .when()
                .post(LOGOUT_USER);

        ApiAllureUtil.attachResponse(response);

        return response;
    }

}