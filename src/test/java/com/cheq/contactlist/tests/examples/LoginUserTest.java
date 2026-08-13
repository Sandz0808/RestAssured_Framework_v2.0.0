package com.cheq.contactlist.tests.examples;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.cheq.contactlist.utilities.SaveResponseUtil;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginUserTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
    }

    @Test
    void testSuccessfulLogin() {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", "sjimena@faker.com");
        requestBody.put("password", "12345qwert");

        // Send login request
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)

                .when()
                .post("/users/login")

                .then()

                // Assertions
                .statusCode(200)
                .body("user.firstName", notNullValue())
                .body("user.lastName", notNullValue())
                .body("user.email", equalTo("sjimena@faker.com"))
                .body("token", notNullValue())
                .log().body()

                // Extract token
                .extract()
                .response();

        SaveResponseUtil.saveResponseBody(response, "LoginResponse");

    }
}