package com.cheq.application.tests.examples;

import com.cheq.application.models.contactlistmodel.userrequestmodel.CreateUser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.cheq.application.utilities.SaveResponseUtil;

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

//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("firstName", "Sandro");
//        requestBody.put("lastName", "Tester");
//        requestBody.put("email", uniqueEmail);
//        requestBody.put("password", "SecurePassword123!");

        CreateUser requestBody = new CreateUser();
        requestBody.setFirstName("Sandro");
        requestBody.setLastName("Tester");
        requestBody.setEmail(uniqueEmail);
        requestBody.setPassword("SecurePassword123!");

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
                .log().headers()
                .extract()
                .response(); // <-- This captures the whole JSON payload

        SaveResponseUtil.saveResponseBody(response, "SignupResponse");



    }
}