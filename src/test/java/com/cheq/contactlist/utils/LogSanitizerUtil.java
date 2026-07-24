package com.cheq.contactlist.utils;
import io.restassured.http.Headers;

import java.util.*;


public final class LogSanitizerUtil {

    private LogSanitizerUtil() {}

    private static final String MASK = "*** Sensitive data is Masked for security! ***";


    private static final Set<String> SENSITIVE_FIELDS = Set.of(

            "password",
            "token",
            "accessToken",
            "refreshToken",
            "authorization",
            "apiKey",
            "clientSecret",
            "secret",
            "_id",
            "owner",
            "Reporting-Endpoints:",
            "Report-To"

    );

    // FOR JSON FORMAT
    public static String sanitize(Object object) {

        if (object == null) {
            return "{}";
        }

        if (object instanceof Map<?, ?> map) {
            return sanitizeMap((Map<?, ?>) map).toString();
        }

        if (object instanceof List<?> list) {
            return sanitizeList(list).toString();
        }

        return sanitizeString(object.toString());

    }


    private static Map<String, Object> sanitizeMap(Map<?, ?> original) {

        Map<String, Object> sanitized = new LinkedHashMap<>();

        original.forEach((key, value) -> {

            String field = String.valueOf(key);

            if (SENSITIVE_FIELDS.contains(field)) {

                sanitized.put(field, MASK);

            } else if (value instanceof Map<?, ?> nestedMap) {

                sanitized.put(field, sanitizeMap(nestedMap));

            } else if (value instanceof List<?> list) {

                sanitized.put(field, sanitizeList(list));

            } else {

                sanitized.put(field, value);

            }

        });

        return sanitized;

    }


    private static List<Object> sanitizeList(List<?> list) {

        List<Object> sanitized = new ArrayList<>();

        for (Object item : list) {

            if (item instanceof Map<?, ?> map) {

                sanitized.add(sanitizeMap(map));

            } else if (item instanceof List<?> nestedList) {

                sanitized.add(sanitizeList(nestedList));

            } else {

                sanitized.add(item);

            }

        }

        return sanitized;

    }


    public static String sanitizeString(String input) {

        if (input == null || input.isBlank()) {
            return "{}";
        }

        String sanitized = input;

        for (String field : SENSITIVE_FIELDS) {

            sanitized = sanitized.replaceAll(
                    "(?i)(\"?"
                            + field
                            + "\"?\\s*:\\s*\").*?\"",
                    "$1" + MASK + "\""
            );

        }

        return sanitized;

    }



    // FOR STRING FORMAT
    public static String sanitizeHeaders(Headers headers) {

        if (headers == null || headers.size() == 0) {
            return "{}";
        }

        StringBuilder sanitized = new StringBuilder();

        headers.forEach(header -> {

            String value = switch (header.getName().toLowerCase()) {

                case "authorization",
                     "proxy-authorization",
                     "cookie",
                     "set-cookie",
                     "x-api-key",
                     "api-key",
                     "access-token",
                     "refresh-token",
                     "x-auth-token",
                     "report-to",
                     "reporting-endpoints",
                     "nel",
                     "server",
                     "via",
                     "x-powered-by" -> MASK;

                default -> header.getValue();

            };

            sanitized.append(header.getName())
                    .append(": ")
                    .append(value)
                    .append(System.lineSeparator());

        });

        return sanitized.toString();

    }

}