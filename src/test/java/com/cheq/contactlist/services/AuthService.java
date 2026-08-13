package com.cheq.contactlist.services;

import com.cheq.contactlist.specifications.RequestSpecs;
import io.restassured.response.Response;
import com.cheq.contactlist.models.userrequestmodel.LoginRequest;
import org.slf4j.Logger;
import com.cheq.contactlist.utilities.LoggerUtil;
import com.cheq.contactlist.utilities.SaveResponseUtil;
import static com.cheq.contactlist.constants.EndpointConstant.*;
import static io.restassured.RestAssured.given;

public class AuthService {

    private static final Logger log =
            LoggerUtil.getLogger(AuthService.class);

    public static Response logoutUser(String token) {

        log.info("========== USER LOGOUT ==========");
        log.info(" Endpoint: {}", LOGOUT_USER);
        log.info(" Method  : POST");

        Response response = given()
                .spec(RequestSpecs.requestSpec(token))
                .when()
                .post(LOGOUT_USER); // e.g., POST /users/logout

        log.info("  Status Code : {}", response.statusCode());
        log.info(" Response Time  : {} ms", response.time());

        return response;
    }


    public static Response loginUser(LoginRequest payload) {

        log.info("========== USER LOGIN ==========");
        log.info("Endpoint : {}", LOGIN_USER);
        log.info("Method   : POST");

        Response response = given()
                .spec(RequestSpecs.requestSpec())
                .body(payload)
                .when()
                .post(LOGIN_USER);

        log.info("Status Code  : {}", response.statusCode());
        log.info("Response Time: {} ms", response.time());


        if (response.statusCode() == 200) {

            SaveResponseUtil.saveResponseBody(response, "LoginResponse");
            log.info("Response saved to LoginResponse.json");

        }

        return response;
    }

}