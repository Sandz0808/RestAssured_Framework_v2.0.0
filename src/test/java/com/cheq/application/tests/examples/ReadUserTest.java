package com.cheq.application.tests.examples;

import io.restassured.RestAssured;
import org.testng.annotations.Test;
import com.cheq.application.utilities.JsonReaderUtil;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ReadUserTest {

    @Test
    public void testReadUserProfile() {
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

        String token = JsonReaderUtil.readData("responses", "SignupResponse", "token");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/users/me")
                .then()
                .statusCode(200)
                .body("firstName", equalTo("Sandro"))
                .log().body();


    }
}