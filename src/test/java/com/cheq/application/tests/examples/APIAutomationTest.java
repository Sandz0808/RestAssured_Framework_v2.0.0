package com.cheq.application.tests.examples;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Test
public class APIAutomationTest {

    @Test(priority = 1)
    public void createUser() {


        // PREPARING THE PRE-REQUISITES
        given()
                .baseUri("https://thinking-tester-contact-list.herokuapp.com")
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "firstName": "Sandro",
                        "lastName": "Jimena",
                        "email": "test8008@example.com",
                        "password": "SecurePassword123!"
                    }
                    """)

                // SENDING THE REQUEST TO THE API USING POST METHOD
                .when()
                .post("/users")

                // VALIDATING THE RESPONSE OF THE API
                .then()
                .statusCode(201)
                .body("user.firstName", equalTo("Sandro"))
                .body("user.lastName", equalTo("Jimena"))
                .extract()
                .path("token");

    }


    @Test(priority = 2)
    public void testGetUser() {

        given()
                .baseUri("https://thinking-tester-contact-list.herokuapp.com")
                .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2YTYzOGRiOTEzZDgzMDAwMTU2OGJlYTIiLCJpYXQiOjE3ODQ5MDkyNDF9.lD_8amlh_C6q3Z_rpZ5X2Hxm-yvGZqiAVJr250fsgV4")

                .when()
                .get("/users/me")

                .then()
                .body("token", nullValue())
                .statusCode(200)
                .body("user.firstName", equalTo("Sandro"));
    }


    @Test(priority = 3)
    public void updateUser() {

        given()
                .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2YTYzOGRiOTEzZDgzMDAwMTU2OGJlYTIiLCJpYXQiOjE3ODQ5MDkyNDF9.lD_8amlh_C6q3Z_rpZ5X2Hxm-yvGZqiAVJr250fsgV4")
                .contentType(ContentType.JSON)
                .body("""
                    {
                        "firstName": "Jayson",
                        "lastName": "Bourne",
                        "email": "UpdateEmail08@examples.com",
                        "password": "SecurePassword123!"
                    }
                    """)

                .when()
                .patch("https://thinking-tester-contact-list.herokuapp.com/users/me")


                .then()
                .log().body();

    }

    @Test(priority = 4)
    public void deleteUser() {


        given()
                .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2YTYzOGRiOTEzZDgzMDAwMTU2OGJlYTIiLCJpYXQiOjE3ODQ5MDkyNDF9.lD_8amlh_C6q3Z_rpZ5X2Hxm-yvGZqiAVJr250fsgV4")

                .when()
                .delete("https://thinking-tester-contact-list.herokuapp.com/users/me")


                .then()
                //.statusCode(200)
                .body("firstName", nullValue());
                //.body(is(emptyString()));

    }
}
