package com.cheq.contactlist.tests.examples;

import io.restassured.RestAssured;
import org.testng.annotations.Test;
import com.cheq.contactlist.utils.JsonReaderUtil;

import static io.restassured.RestAssured.*;

public class DeleteUserTest {

    @Test
    public void testDeleteUser() {
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

        String token = JsonReaderUtil.readData("responses", "SignupResponse", "token");

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/users/me")
                .then()
                .statusCode(200)
                .log().all();


    }
}