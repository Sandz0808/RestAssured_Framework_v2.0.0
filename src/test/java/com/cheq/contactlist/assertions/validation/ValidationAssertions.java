package com.cheq.contactlist.assertions.validation;

import com.cheq.contactlist.utils.LoggerUtil;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.testng.Assert;

import java.util.List;

public class ValidationAssertions {

    private static final Logger log =
            LoggerUtil.getLogger(ValidationAssertions.class);


    public static void verifyFieldEquals(
            Response response,
            String jsonPath,
            Object expectedValue) {

        log.info("Verifying field '{}' equals expected value.", jsonPath);

        try {

            Allure.step("Verify Field Equals: " + jsonPath, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                Object actualValue = response.jsonPath().get(jsonPath);

                Allure.addAttachment(
                        "Field Validation",
                        String.format(
                                "Field    : %s%nExpected : %s%nActual   : %s",
                                jsonPath,
                                expectedValue,
                                actualValue));

                Assert.assertEquals(
                        actualValue,
                        expectedValue,
                        "Field value does not match.");

            });

            log.info("Field '{}' equality verification passed.", jsonPath);

        } catch (AssertionError e) {

            log.error("Field '{}' equality verification failed.", jsonPath, e);
            throw e;
        }
    }


    public static void verifyFieldNotEquals(
            Response response,
            String jsonPath,
            Object expectedValue) {

        log.info("Verifying field '{}' does not equal expected value.", jsonPath);

        try {

            Allure.step("Verify Field Does Not Equal: " + jsonPath, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                Object actualValue = response.jsonPath().get(jsonPath);

                Allure.addAttachment(
                        "Field Validation",
                        String.format(
                                "Field    : %s%nUnexpected : %s%nActual      : %s",
                                jsonPath,
                                expectedValue,
                                actualValue));

                Assert.assertNotEquals(
                        actualValue,
                        expectedValue,
                        "Unexpected field value.");

            });

            log.info("Field '{}' inequality verification passed.", jsonPath);

        } catch (AssertionError e) {

            log.error("Field '{}' inequality verification failed.", jsonPath, e);
            throw e;
        }
    }

    public static void verifyFieldExists(
            Response response,
            String jsonPath) {

        log.info("Verifying field '{}' exists.", jsonPath);

        try {

            Allure.step("Verify Field Exists: " + jsonPath, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                Object value = response.jsonPath().get(jsonPath);

                Allure.addAttachment(
                        "Field Validation",
                        String.format(
                                "Field Name : %s%nActual Value: %s",
                                jsonPath,
                                value));

                Assert.assertNotNull(
                        value,
                        "Field does not exist.");

            });

            log.info("Field '{}' existence verification passed.", jsonPath);

        } catch (AssertionError e) {

            log.error("Field '{}' existence verification failed.", jsonPath, e);
            throw e;
        }
    }


    public static void verifyFieldDoesNotExist(
            Response response,
            String jsonPath) {

        log.info("Verifying field '{}' does not exist.", jsonPath);

        try {

            Allure.step("Verify Field Does Not Exist: " + jsonPath, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                Object value = response.jsonPath().get(jsonPath);

                Allure.addAttachment(
                        "Field Validation",
                        String.format(
                                "Field Name : %s%nActual Value: %s",
                                jsonPath,
                                value));

                Assert.assertNull(
                        value,
                        "Field should not exist.");

            });

            log.info("Field '{}' absence verification passed.", jsonPath);

        } catch (AssertionError e) {

            log.error("Field '{}' absence verification failed.", jsonPath, e);
            throw e;
        }
    }


    public static void verifyFieldIsNotNull(
            Response response,
            String jsonPath) {

        log.info("Verifying field '{}' is not null.", jsonPath);

        try {

            Allure.step("Verify Field Is Not Null: " + jsonPath, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                Object value = response.jsonPath().get(jsonPath);

                Allure.addAttachment(
                        "Field Validation",
                        String.format(
                                "Field Name : %s%nActual Value: %s",
                                jsonPath,
                                value));

                Assert.assertNotNull(
                        value,
                        "Field is null.");

            });

            log.info("Field '{}' not-null verification passed.", jsonPath);

        } catch (AssertionError e) {

            log.error("Field '{}' not-null verification failed.", jsonPath, e);
            throw e;
        }
    }


    public static void verifyFieldContains(
            Response response,
            String jsonPath,
            String expectedText) {

        log.info("Verifying field '{}' contains '{}'.", jsonPath, expectedText);

        try {

            Allure.step("Verify Field Contains: " + jsonPath, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                String value = response.jsonPath().getString(jsonPath);

                Allure.addAttachment(
                        "Field Validation",
                        String.format(
                                "Field Name : %s%nExpected   : %s%nActual     : %s",
                                jsonPath,
                                expectedText,
                                value));

                Assert.assertTrue(
                        value.contains(expectedText),
                        "Expected text not found.");

            });

            log.info("Field '{}' contains verification passed.", jsonPath);

        } catch (AssertionError e) {

            log.error("Field '{}' contains verification failed.", jsonPath, e);
            throw e;
        }
    }


    public static void verifyCollectionSize(
            Response response,
            String jsonPath,
            int expectedSize) {

        log.info("Verifying collection '{}' size.", jsonPath);

        try {

            Allure.step("Verify Collection Size: " + jsonPath, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                List<?> list = response.jsonPath().getList(jsonPath);

                Allure.addAttachment(
                        "Collection Size",
                        String.format(
                                "Expected Size : %d%nActual Size   : %d",
                                expectedSize,
                                list.size()));

                Assert.assertEquals(
                        list.size(),
                        expectedSize,
                        "Collection size mismatch.");

            });

            log.info("Collection '{}' size verification passed.", jsonPath);

        } catch (AssertionError e) {

            log.error("Collection '{}' size verification failed.", jsonPath, e);
            throw e;
        }
    }


    public static void verifyCollectionContains(
            Response response,
            String jsonPath,
            Object expectedValue) {

        log.info("Verifying collection '{}' contains '{}'.", jsonPath, expectedValue);

        try {

            Allure.step("Verify Collection Contains: " + jsonPath, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                List<?> list = response.jsonPath().getList(jsonPath);

                Allure.addAttachment(
                        "Collection Validation",
                        String.format(
                                "Expected Value : %s%nCollection     : %s",
                                expectedValue,
                                list));

                Assert.assertTrue(
                        list.contains(expectedValue),
                        "Expected value not found.");

            });

            log.info("Collection '{}' content verification passed.", jsonPath);

        } catch (AssertionError e) {

            log.error("Collection '{}' content verification failed.", jsonPath, e);
            throw e;
        }
    }


    public static <T extends Comparable<T>> void verifyGreaterThan(
            Response response,
            String jsonPath,
            T expectedValue) {

        log.info("Verifying '{}' is greater than '{}'.", jsonPath, expectedValue);

        try {

            Allure.step("Verify Greater Than: " + jsonPath, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                T actualValue = response.jsonPath().get(jsonPath);

                Allure.addAttachment(
                        "Numeric Validation",
                        String.format(
                                "Expected > %s%nActual     : %s",
                                expectedValue,
                                actualValue));

                Assert.assertTrue(
                        actualValue.compareTo(expectedValue) > 0,
                        "Value is not greater than expected.");

            });

            log.info("Greater-than verification passed for '{}'.", jsonPath);

        } catch (AssertionError e) {

            log.error("Greater-than verification failed for '{}'.", jsonPath, e);
            throw e;
        }
    }


    public static void verifyFieldMatchesRegex(
            Response response,
            String jsonPath,
            String regex) {

        log.info("Verifying field '{}' matches regex.", jsonPath);

        try {

            Allure.step("Verify Field Matches Regex: " + jsonPath, () -> {

                Assert.assertNotNull(
                        response,
                        "Response object is null.");

                String value = response.jsonPath().getString(jsonPath);

                Allure.addAttachment(
                        "Regex Validation",
                        String.format(
                                "Field Name : %s%nRegex      : %s%nActual     : %s",
                                jsonPath,
                                regex,
                                value));

                Assert.assertTrue(
                        value.matches(regex),
                        "Field format is invalid.");

            });

            log.info("Regex verification passed for '{}'.", jsonPath);

        } catch (AssertionError e) {

            log.error("Regex verification failed for '{}'.", jsonPath, e);
            throw e;
        }
    }

}