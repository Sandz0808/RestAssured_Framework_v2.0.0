package com.cheq.contactlist.services;

import io.restassured.response.Response;
import com.cheq.contactlist.models.userrequestmodel.LoginRequest;
import org.slf4j.Logger;
import com.cheq.contactlist.utils.LoggerUtil;
import com.cheq.contactlist.utils.SaveResponseUtil;
import static com.cheq.contactlist.constants.EndpointConstant.*;
import static com.cheq.contactlist.specs.RequestSpec.*;
import static io.restassured.RestAssured.given;

public class AuthService {

    private static final Logger log =
            LoggerUtil.getLogger(AuthService.class);

    public static Response logoutUser(String token) {

        log.info("========== USER LOGOUT ==========");
        log.info(" Endpoint: {}", LOGOUT_USER);
        log.info(" Method  : POST");

        Response response = given()
                .spec(authRequestSpec(token))
                .when()
                .post(LOGOUT_USER); // e.g., POST /users/logout

        if (response.statusCode() == 200) {

            log.info("  Status Code : {}", response.statusCode());
            log.info(" Response Time  : {} ms", response.time());

        } else {

            log.error("Failed to logout user");
            org.testng.Assert.fail("Failed to logout user.");

        }

        return response;
    }


    public static Response loginUser(LoginRequest payload) {

        log.info(" ==========  USER LOGIN  ==========");
        log.info("Endpoint: {}", LOGIN_USER);
        log.info(" Method : POST");

        Response response =  given()
                .spec(getRequestSpec())
                .body(payload)
                .when()
                .post(LOGIN_USER); // e.g., POST /users/login

        if (response.statusCode() == 200) {

            log.info("Status Code : {}", response.statusCode());
            log.info("Response Time  : {} ms", response.time());

        } else {

            log.error("Failed to login user");
            org.testng.Assert.fail("Failed to login user.");

        }

        SaveResponseUtil.saveResponseBody(response, "LoginResponse");
        log.info(" Response saved to UpdateUserResponse.json");

        return response;
    }

}