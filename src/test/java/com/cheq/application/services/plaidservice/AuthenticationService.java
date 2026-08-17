package com.cheq.application.services.plaidservice;

import com.cheq.application.models.plaidmodel.authenticationmodel.CreateProcessorToken;
import com.cheq.application.models.plaidmodel.authenticationmodel.CreatePublicToken;
import com.cheq.application.specifications.RequestSpecs;
import com.cheq.application.utilities.LoggerUtil;
import io.restassured.response.Response;
import org.slf4j.Logger;

import static com.cheq.application.constants.plaidconstant.PlaidEndpointConstant.*;
import static io.restassured.RestAssured.given;

public class AuthenticationService {

    private static final Logger log =
            LoggerUtil.getLogger(com.cheq.application.services.contactlistservice.AuthenticationService.class);

    public static Response createProcessorToken(CreateProcessorToken payload) {

        Response response =  given()
                .spec(RequestSpecs.requestSpec())
                .body(payload)
                .when()
                .post(CREATE_PROCESSOR_TOKEN);

        log.info("PLAID STATUS: {}", response.statusCode());
        log.info("PLAID RESPONSE: {}", response.asPrettyString());

        return response;
    }

    public static Response createPublicToken(CreatePublicToken payload) {

        Response response =  given()
                .spec(RequestSpecs.requestSpec())
                .body(payload)
                .when()
                .post(CREATE_PUBLIC_TOKEN);

        log.info("PLAID STATUS: {}", response.statusCode());
        log.info("PLAID RESPONSE: {}", response.asPrettyString());

        return response;
    }


}