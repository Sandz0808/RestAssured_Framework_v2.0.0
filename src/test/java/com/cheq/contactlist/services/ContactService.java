package com.cheq.contactlist.services;

import com.cheq.contactlist.specifications.RequestSpecs;
import io.restassured.response.Response;
import com.cheq.contactlist.models.contactrequestmodel.CreateContact;
import com.cheq.contactlist.models.contactrequestmodel.UpdateContact;
import org.slf4j.Logger;
import com.cheq.contactlist.utilities.LoggerUtil;
import com.cheq.contactlist.utilities.SaveResponseUtil;
import static com.cheq.contactlist.constants.EndpointConstant.*;
import static io.restassured.RestAssured.given;

public class ContactService {

    private static final Logger log = LoggerUtil.getLogger(ContactService.class);

    // =================================================================
    // CREATE: Add a new contact (Requires Auth Token & Body)
    // =================================================================
    public static Response addContact(String token, CreateContact payload) {
        log.info("========== ADD CONTACT ==========");
        log.info("Endpoint : {}", ADD_CONTACT);
        log.info("Method   : POST");
        log.info("Contact  : {} {}", payload.getFirstName(), payload.getLastName());

        Response response = given()
                .spec(RequestSpecs.requestSpec(token))
                .body(payload)
                .when()
                .post(ADD_CONTACT);

        log.info("Status Code   : {}", response.statusCode());
        log.info("Response Time : {} ms", response.time());

        if (response.statusCode() == 201) {

            SaveResponseUtil.saveResponseBody(response, "addContactResponse");
            log.info("Response saved to addContactResponse.json");

        }

        return response;
    }

    // =================================================================
    // READ: Get the full Contact List (Requires Auth Token)
    // =================================================================
    public static Response getContactList(String token) {
        return given()
                .spec(RequestSpecs.requestSpec(token))
                .when()
                .get(GET_CONTACT_LIST); // e.g., GET /contacts

    }

    // =================================================================
    // READ: Get a single Contact by ID (Requires Auth Token & Path Param)
    // =================================================================

    public static Response getContact(String token, String contactId) {

        log.info("==========  READ CONTACT ==========");
        log.info("Endpoint: {}", GET_CONTACT);
        log.info("Method  : GET");

        Response response = given()
                .spec(RequestSpecs.requestSpec(token))
                .pathParam("id", contactId)
                .when()
                .get(GET_CONTACT, contactId); // e.g., GET /contacts/{id}

        log.info("Status Code  : {}", response.statusCode());
        log.info(" Response Time : {} ms", response.time());

            return response;
        }

    // =================================================================
    // UPDATE: Update a Contact fully (Requires Auth Token, Path Param, & Body)
    // =================================================================
    public static Response updateContact(String token, String contactId, UpdateContact payload) {
        log.info("==========  UPDATE CONTACT ========== ");
        log.info(" Endpoint: {} ", UPDATE_CONTACT);
        log.info("Method  : PUT ");
        log.info("Contact : {} {}", payload.getFirstName(), payload.getLastName());

        Response response = given()
                .spec(RequestSpecs.requestSpec(token))
                .pathParam("id", contactId)
                .body(payload)
                .when()
                .put(PUT_CONTACT); // e.g., PUT /contacts/{id}

        log.info(" Status Code  : {}", response.statusCode());
        log.info(" Response Time: {} ms", response.time());

        return response;
    }

    // =================================================================
    // PATCH: Partial update on a Contact (Requires Auth Token, Path Param, & Body)
    // =================================================================
    public static Response patchContact(String token, String contactId, CreateContact payload) {
        Response response =  given()
                .spec(RequestSpecs.requestSpec(token))
                .pathParam("id", contactId)
                .body(payload)
                .when()
                .patch(UPDATE_CONTACT); // e.g., PATCH /contacts/{id}

        log.info("Status Code : {}", response.statusCode());
        log.info("Response Time: {} ms", response.time());

        return response;
        }


    // =================================================================
    // DELETE: Remove a Contact (Requires Auth Token & Path Param)
    // =================================================================
    public static Response deleteContact(String token, String contactId) {
        log.info("==========  DELETE CONTACT  ==========");
        log.info("  Endpoint: {}", DELETE_CONTACT);
        log.info(" Method  : DELETED");

        Response response =  given()
                .spec(RequestSpecs.requestSpec(token))
                .pathParam("id", contactId)
                .when()
                .delete(DELETE_CONTACT); // e.g., DELETE /contacts/{id}

        log.info("Status  Code : {}", response.statusCode());
        log.info("Response  Time: {} ms", response.time());

        return response;
    }

    public static Response addContactWithIdempotencyKey(
            String token,
            CreateContact payload,
            String idempotencyKey) {

        log.info(" ========== ADD CONTACT WITH IDEMPOTENCY ==========");
        log.info(" Endpoint : {}", ADD_CONTACT);
        log.info(" Method   : POST");

        return given()
                .spec(RequestSpecs.requestSpec(token))
                .header("Idempotency-Key", idempotencyKey)
                .body(payload)
                .post(ADD_CONTACT);
    }



}