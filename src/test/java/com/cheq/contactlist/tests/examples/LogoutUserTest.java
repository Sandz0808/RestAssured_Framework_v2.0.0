package com.cheq.contactlist.tests.examples;

import io.restassured.RestAssured;
import org.testng.annotations.Test;
import com.cheq.contactlist.utils.JsonReaderUtil;

import static io.restassured.RestAssured.*;

public class LogoutUserTest {

    @Test
    public void testLogoutUser() {
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

        String token = JsonReaderUtil.readData("responses", "LoginToken", "token");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/users/logout")
                .then()
                .statusCode(200)
                .log().all();


    }
}