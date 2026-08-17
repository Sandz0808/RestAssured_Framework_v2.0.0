package com.cheq.application.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SaveResponseUtil {

    private static final Logger log = LoggerUtil.getLogger(SaveResponseUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void saveResponseBody(Response response, String fileName) {

        try {

            File file = new File("src/test/resources/responses/" + fileName + ".json");

            Object json = mapper.readValue(response.asString(), Object.class);

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, json);

        } catch (IOException e) {
            throw new RuntimeException("Unable to save response.", e);
        }
    }

    public static void saveResponseHeaders(
            Response response,
            String fileName) {

        try {

            File file = new File(
                    "src/test/resources/responses/" + fileName + ".json");

            Map<String, String> headers = new LinkedHashMap<>();

            for (Header header : response.getHeaders()) {

                headers.put(
                        header.getName(),
                        header.getValue());

            }

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, headers);

            log.info("Response headers saved successfully: {}", file.getName());

        } catch (IOException e) {

            log.error(
                    "Unable to save response headers: {}",
                    fileName,
                    e);

            throw new RuntimeException(
                    "Unable to save response headers.",
                    e);

        }

    }

}