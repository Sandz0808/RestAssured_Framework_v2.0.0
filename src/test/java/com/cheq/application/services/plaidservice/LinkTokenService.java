package com.cheq.application.services.plaidservice;

import com.cheq.application.models.plaidmodel.linktokenmodel.CreateLinkToken;
import com.cheq.application.payloads.plaidpayload.CreateLinkTokenPayload;
import com.cheq.application.services.contactlistservice.AuthenticationService;
import com.cheq.application.specifications.RequestSpecs;
import com.cheq.application.utilities.LoggerUtil;
import io.restassured.response.Response;
import org.slf4j.Logger;

import static com.cheq.application.constants.plaidconstant.PlaidEndpointConstant.*;
import static io.restassured.RestAssured.given;


public class LinkTokenService {

    private static final Logger log =
            LoggerUtil.getLogger(AuthenticationService.class);

    public static Response createLinkToken() {

        CreateLinkToken payload = CreateLinkTokenPayload.createValidLinkToken();

        Response response = given()
                .spec(RequestSpecs.requestSpec())
                .body(payload)
                .when()
                .post(CREATE_LINK_TOKEN);

        log.info("PLAID STATUS: {}", response.statusCode());
        log.info("PLAID RESPONSE: {}", response.asPrettyString());

        return response;

    }

    public static Response createHostedLinkToken() {

        CreateLinkToken payload =
                CreateLinkTokenPayload.createHostedLinkToken();

        Response response = given()
                .spec(RequestSpecs.requestSpec())
                .body(payload)
                .when()
                .post(CREATE_LINK_TOKEN);

        log.info("PLAID STATUS: {}", response.statusCode());
        log.info("PLAID RESPONSE: {}", response.asPrettyString());

        return response;
    }
}