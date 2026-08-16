package com.cheq.contactlist.services;

import com.cheq.contactlist.specifications.RequestSpecs;
import io.restassured.response.Response;
import com.cheq.contactlist.models.userrequestmodel.*;
import org.slf4j.Logger;
import com.cheq.contactlist.utilities.LoggerUtil;
import com.cheq.contactlist.utilities.SaveResponseUtil;
import static com.cheq.contactlist.constants.EndpointConstant.*;
import static io.restassured.RestAssured.given;


public class UserService {

    private static final Logger log = LoggerUtil.getLogger(UserService.class);

    public static Response createUser(CreateUser payload) {

        log.info("========== CREATE USER ==========");
        log.info("Endpoint : {}", CREATE_USER);
        log.info("Method   : POST");
        log.info("User  : {} {}", payload.getFirstName(), payload.getLastName());

        Response response = given()
                .spec(RequestSpecs.requestSpec())
                .body(payload)
                .when()
                .post(CREATE_USER); // e.g., POST /


        log.info("Status Code   : {}", response.statusCode());
        log.info("Response Time : {} ms", response.time());


        if (response.statusCode() == 201) {

            SaveResponseUtil.saveResponseBody(response, "SignupResponse");
            log.info(" Response saved to SignupResponse.json");
        }
            return response;
        }


    public static Response readUser(String token) {

        log.info(" ========== READ USER ==========");
        log.info(" Endpoint  : {}", GET_USER);
        log.info(" Method   : GET");

        Response response = given()
                .spec(RequestSpecs.requestSpec(token))
                .when()
                .get(GET_USER); // e.g., GET /users/me

        log.info("Status Code  : {}", response.statusCode());
        log.info("Response Time: {} ms", response.time());

        return response;
    }

    public static Response updateUser(String token, UpdateUser payload) {

        log.info("========== UPDATE USER ==========");
        log.info(" Endpoint : {}", UPDATE_USER);
        log.info("Method   : PATCH");
        log.info("User     : {} {}", payload.getFirstName(), payload.getLastName());

        Response response = given()
                .spec(RequestSpecs.requestSpec(token))
                .body(payload)
                .when()
                .patch(UPDATE_USER);

        log.info(" Status Code   : {}", response.statusCode());
        log.info(" Response Time : {} ms", response.time());

        return response;
    }


    public static Response deleteUser(String token) {

        log.info(" ========== USER LOGIN  ==========");
        log.info("  Endpoint:  {}", DELETE_USER);
        log.info("Method : DELETED");

        Response response = given()
                .spec(RequestSpecs.requestSpec(token))
                .when()
                .delete(DELETE_USER); // e.g., DELETE /users/me

        log.info(" Status Code  : {}", response.statusCode());
        log.info(" Response Time: {} ms", response.time());

        return response;
    }

    public static Response createUserWithIdempotencyKey(
            CreateUser payload,
            String idempotencyKey) {

        log.info(" ========== CREATE USER WITH IDEMPOTENCY ==========");
        log.info("Endpoint  : {}", CREATE_USER);
        log.info("Method  : POST");

        return given()
                .spec(RequestSpecs.requestSpec())
                .header("Idempotency-Key", idempotencyKey)
                .body(payload)
                .post(CREATE_USER);
    }
}