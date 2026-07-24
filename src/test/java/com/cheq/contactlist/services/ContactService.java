package com.cheq.contactlist.services;

import io.restassured.response.Response;
import com.cheq.contactlist.models.contactrequestmodel.CreateContact;
import com.cheq.contactlist.models.contactrequestmodel.UpdateContact;
import org.slf4j.Logger;
import com.cheq.contactlist.utils.LoggerUtil;
import com.cheq.contactlist.utils.SaveResponseUtil;
import static com.cheq.contactlist.constants.EndpointConstant.*;
import static io.restassured.RestAssured.given;
import static com.cheq.contactlist.specs.RequestSpec.authRequestSpec;

public class ContactService {

    private static final Logger log = LoggerUtil.getLogger(ContactService.class);

    // =================================================================
    // CREATE: Add a new contact (Requires Auth Token & Body)
    // =================================================================
    public static Response addContact(String token, CreateContact payload) {

        log.info("========================================");
        log.info("========== ADD CONTACT ==========");
        log.info("Endpoint : {}", ADD_CONTACT);
        log.info("Method   : POST");
        log.info("Contact  : {} {}", payload.getFirstName(), payload.getLastName());

        Response response = given()
                .spec(authRequestSpec(token))
                .body(payload)
                .when()
                .post(ADD_CONTACT);

        if (response.statusCode() == 201) {
            log.info("Status Code   : {}", response.statusCode());
            log.info("Response Time : {} ms", response.time());


        } else {
            log.error("Failed to create contact.");
            org.testng.Assert.fail("Failed to create contact.");
        }

        SaveResponseUtil.saveResponse(response, "AddContactResponse");
        log.info("Response saved to AddContactResponse.json");

        return response;

    }

    // =================================================================
    // READ: Get the full Contact List (Requires Auth Token)
    // =================================================================
    public static Response getContactList(String token) {
        return given()
                .spec(authRequestSpec(token))
                .when()
                .get(GET_CONTACT_LIST); // e.g., GET /contacts

    }

    // =================================================================
    // READ: Get a single Contact by ID (Requires Auth Token & Path Param)
    // =================================================================

    public static Response getContact(String token, String contactId) {

        log.info("======================================== ");
        log.info("==========  READ CONTACT ==========");
        log.info("Endpoint: {}", GET_CONTACT);
        log.info("Method  : GET");

        Response response = given()
                .spec(authRequestSpec(token))
                .pathParam("id", contactId)
                .when()
                .get(GET_CONTACT, contactId); // e.g., GET /contacts/{id}

        if (response.statusCode() == 200) {
            log.info("Status Code  : {}", response.statusCode());
            log.info(" Response Time : {} ms", response.time());


        } else {
            log.error(" Failed to read contact.");
            org.testng.Assert.fail("Failed to read contact.");
        }

        return response;


    }

    // =================================================================
    // UPDATE: Update a Contact fully (Requires Auth Token, Path Param, & Body)
    // =================================================================
    public static Response updateContact(String token, String contactId, UpdateContact payload) {
        log.info(" ========================================");
        log.info("==========  UPDATE CONTACT ========== ");
        log.info(" Endpoint: {} ", UPDATE_CONTACT);
        log.info("Method  : PUT ");
        log.info("Contact : {} {}", payload.getFirstName(), payload.getLastName());

        return given()
                .spec(authRequestSpec(token))
                .pathParam("id", contactId)
                .body(payload)
                .when()
                .put(PUT_CONTACT); // e.g., PUT /contacts/{id}
    }

    // =================================================================
    // PATCH: Partial update on a Contact (Requires Auth Token, Path Param, & Body)
    // =================================================================
    public static Response patchContact(String token, String contactId, CreateContact payload) {
        return given()
                .spec(authRequestSpec(token))
                .pathParam("id", contactId)
                .body(payload)
                .when()
                .patch(UPDATE_CONTACT); // e.g., PATCH /contacts/{id}
    }

    // =================================================================
    // DELETE: Remove a Contact (Requires Auth Token & Path Param)
    // =================================================================
    public static Response deleteContact(String token, String contactId) {
        log.info(" ======================================== ");
        log.info("==========  DELETE CONTACT  ==========");
        log.info("  Endpoint: {}", DELETE_CONTACT);
        log.info(" Method  : DELETED");
        return given()
                .spec(authRequestSpec(token))
                .pathParam("id", contactId)
                .when()
                .delete(DELETE_CONTACT); // e.g., DELETE /contacts/{id}
    }
}