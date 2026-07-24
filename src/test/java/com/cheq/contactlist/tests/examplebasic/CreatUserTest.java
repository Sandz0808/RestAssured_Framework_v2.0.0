package com.cheq.contactlist.tests.examplebasic;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response; // 1. IMPORT the RestAssured Response object
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.cheq.contactlist.utils.SaveResponseUtil;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CreatUserTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
    }

    @Test(priority = 1)
    public void testSuccessfulUserSignup() {
        String uniqueEmail = "user_" + System.currentTimeMillis() + "@example.com";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("firstName", "Sandro");
        requestBody.put("lastName", "Tester");
        requestBody.put("email", uniqueEmail);
        requestBody.put("password", "SecurePassword123!");

        // 2. Extract the entire Response object instead of just a string path
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)

                .when()
                .post("/users")

                // Assertions
                .then()
                .statusCode(201)
                .body("user.firstName", equalTo("Sandro"))
                .body("user.lastName", equalTo("Tester"))
                .body("user.email", equalTo(uniqueEmail))
                .body("token", notNullValue())
                .log().body()
                .extract()
                .response(); // <-- This captures the whole JSON payload

        SaveResponseUtil.saveResponse(response, "SignupResponse");



    }
}