package com.cheq.contactlist.services;

import com.cheq.contactlist.models.userrequestmodel.LoginRequest;
import io.restassured.response.Response;
import com.cheq.contactlist.models.userrequestmodel.CreateUser;
import com.cheq.contactlist.models.userrequestmodel.UpdateUser;
import org.slf4j.Logger;
import com.cheq.contactlist.utils.LoggerUtil;
import com.cheq.contactlist.utils.SaveResponseUtil;
import static com.cheq.contactlist.constants.EndpointConstant.*;
import static io.restassured.RestAssured.given;
import static com.cheq.contactlist.specs.RequestSpec.authRequestSpec;
import static com.cheq.contactlist.specs.RequestSpec.getRequestSpec;


public class UserService {

    private static final Logger log = LoggerUtil.getLogger(UserService.class);

    public static Response createUser(CreateUser payload) {

        log.info("========== CREATE USER ==========");
        log.info("Endpoint : {}", CREATE_USER);
        log.info("Method   : POST");
        log.info("User  : {} {}", payload.getFirstName(), payload.getLastName());

        Response response = given()
                .spec(getRequestSpec())
                .body(payload)
                .when()
                .post(CREATE_USER); // e.g., POST /

        if (response.statusCode() == 201) {
            log.info("Status Code   : {}", response.statusCode());
            log.info("Response Time : {} ms", response.time());


        } else {
            log.error("Failed to create contact.");
            org.testng.Assert.fail("Failed to create user.");
        }

        SaveResponseUtil.saveResponse(response, "SignupResponse");
        log.info("Response saved to SignupResponse.json");

        return response;
    }


    public static Response readUser(String token) {

        log.info(" ========== READ USER ==========");
        log.info(" Endpoint  : {}", GET_USER);
        log.info(" Method   : GET");

        Response response = given()
                .spec(authRequestSpec(token))
                .when()
                .get(GET_USER); // e.g., GET /users/me

        if (response.statusCode() == 200) {
            log.info("Status Code  : {}", response.statusCode());
            log.info("Response Time: {} ms", response.time());


        } else {
            log.error("Failed to GET user");
            org.testng.Assert.fail("Failed to GET user.");
        }

        return response;

    }


    public static Response updateUser(String token, UpdateUser payload) {

        log.info("========== UPDATE USER ==========");
        log.info(" Endpoint : {}", UPDATE_USER);
        log.info("Method   : PATCH");
        log.info("User     : {} {}", payload.getFirstName(), payload.getLastName());

        Response response = given()
                .spec(authRequestSpec(token))
                .body(payload)
                .when()
                .patch(UPDATE_USER);

        if (response.statusCode() == 200) {

            log.info(" Status Code   : {}", response.statusCode());
            log.info(" Response Time : {} ms", response.time());

        } else {

            log.error("Failed to update user.");
            org.testng.Assert.fail("Failed to update user.");

        }

        SaveResponseUtil.saveResponse(response, "UpdateUserResponse");
        log.info("Response saved to UpdateUserResponse.json");

        return response;
    }


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

        SaveResponseUtil.saveResponse(response, "LoginResponse");
        log.info(" Response saved to UpdateUserResponse.json");

        return response;
    }


    public static Response deleteUser(String token) {

        log.info(" ========== USER LOGIN  ==========");
        log.info("  Endpoint:  {}", DELETE_USER);
        log.info("Method : DELETED");

        Response response = given()
                .spec(authRequestSpec(token))
                .when()
                .delete(DELETE_USER); // e.g., DELETE /users/me

        if (response.statusCode() == 200) {
            log.info(" Status Code  : {}", response.statusCode());
            log.info(" Response Time: {} ms", response.time());

        } else {
            log.error(" Failed to create contact");
            org.testng.Assert.fail("Failed to create user.");
        }

        return response;
    }
}