package com.cheq.contactlist.assertions.schema;

import com.cheq.contactlist.utils.LoggerUtil;
import io.qameta.allure.Allure;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.Assert;

import java.io.InputStream;

public class SchemaAssertions {

    private static final Logger log =
            LoggerUtil.getLogger(SchemaAssertions.class);

    /**
     * ==========================================================
     * VERIFY SCHEMA
     * ==========================================================
     * Verifies the response against the specified schema type.
     * ==========================================================
     */
    public static void verifySchema(
            Response response,
            SchemaType schemaType) {

        schemaValidator(response, schemaType.getPath());
    }

    /**
     * ==========================================================
     * VERIFY JSON SCHEMA
     * ==========================================================
     * Validates that the response body matches the expected
     * JSON Schema.
     *
     * Usage:
     *
     * SchemaAssertions.verifySchema(
     *      response,
     *      SchemaType.CREATE_USER_SCHEMA);
     * ==========================================================
     */
    public static void schemaValidator(
            Response response,
            String schemaPath) {

        log.info("Verifying JSON Schema: {}", schemaPath);

        try {

            Allure.step("Verify JSON Schema", () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                Assert.assertNotNull(
                        schemaPath,
                        "Schema path is null.");

                InputStream schema =
                        Thread.currentThread()
                                .getContextClassLoader()
                                .getResourceAsStream(schemaPath);

                Assert.assertNotNull(
                        schema,
                        "Schema file not found: " + schemaPath);

                Allure.addAttachment(
                        "Schema File",
                        "text/plain",
                        schemaPath);

                response.then()
                        .assertThat()
                        .body(JsonSchemaValidator.matchesJsonSchema(schema));

                Allure.addAttachment(
                        "Schema Validation Result",
                        "text/plain",
                        "Schema validation passed successfully.");

            });

            log.info("JSON Schema validation passed: {}", schemaPath);

        } catch (AssertionError e) {

            log.error("JSON Schema validation failed: {}", schemaPath, e);
            throw e;
        }
    }

    /**
     * ==========================================================
     * SCHEMA TYPES
     * ==========================================================
     */
    public enum SchemaType {

        CREATE_USER_SCHEMA("schemas/CreateUserSchema.json"),
        LOGIN("schemas/LoginSchema.json"),
        CREATE_CONTACT_SCHEMA("schemas/CreateContactSchema.json"),
        UPDATE_USER("schemas/UpdateUserSchema.json");

        private final String path;

        SchemaType(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }

}