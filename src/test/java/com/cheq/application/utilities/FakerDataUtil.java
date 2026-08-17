package com.cheq.application.utilities;

import com.github.javafaker.Faker;

import java.time.format.DateTimeFormatter;

public final class FakerDataUtil {

    private static final Faker faker = new Faker();

    private FakerDataUtil() {
    }

    // ==========================================================
    // PERSON
    // ==========================================================

    public static String firstName() {
        return faker.name().firstName();
    }

    public static String lastName() {
        return faker.name().lastName();
    }

    // ==========================================================
    // AUTHENTICATION
    // ==========================================================

    public static String email() {
        return faker.internet().emailAddress();
    }

    public static String password() {
        return faker.internet().password(8, 16, true, true, true);
    }

    // ==========================================================
    // CONTACT
    // ==========================================================

    public static String phone() {
        return faker.phoneNumber().cellPhone();
    }

    public static String street() {
        return faker.address().streetAddress();
    }

    public static String secondaryAddress() {
        return faker.address().secondaryAddress();
    }

    public static String city() {
        return faker.address().city();
    }

    public static String state() {
        return faker.address().state();
    }

    public static String postalCode() {
        return faker.address().zipCode();
    }

    public static String country() {
        return faker.address().country();
    }

    // ==========================================================
    // DATE
    // ==========================================================

    public static String birthdate() {

        return faker.date()
                .birthday(18, 60)
                .toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate()
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    // ==========================================================
    // GENERIC TEXT
    // ==========================================================

    public static String text() {
        return faker.lorem().sentence();
    }

    public static String word() {
        return faker.lorem().word();
    }

    // ==========================================================
    // UNIQUE VALUE
    // ==========================================================

    public static String uniqueId() {
        return faker.idNumber().valid();
    }
}