package com.cheq.contactlist.utilities;

import java.util.UUID;

public class TestDataRandomizer {

    public static String randomData(String value) {

        String random = UUID.randomUUID()
                .toString()
                .substring(0, 8);

        return value + random;
    }
}