package com.cheq.contactlist.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.slf4j.Logger;
import com.cheq.contactlist.utils.LoggerUtil;

import java.io.File;
import java.io.IOException;

public class SaveResponseUtil {

    private static final Logger log = LoggerUtil.getLogger(SaveResponseUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void saveResponse(Response response, String fileName) {

        try {

            File file = new File("src/test/resources/responses/" + fileName + ".json");

            Object json = mapper.readValue(response.asString(), Object.class);

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, json);

        } catch (IOException e) {
            throw new RuntimeException("Unable to save response.", e);
        }

    }

}