package com.cheq.contactlist.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public class JsonReaderUtil {

    private static final Logger log = LoggerUtil.getLogger(JsonReaderUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonReaderUtil() {
        // Prevent instantiation
    }

    /**
     * Reads a value from a JSON file.
     *
     * @param folder   Folder under src/test/resources
     * @param fileName JSON file name without .json
     * @param key      JSON key to retrieve
     * @return Value of the key
     */
    public static String readData(String folder, String fileName, String key) {

        try {

            File file = new File(
                    "src/test/resources/" + folder + "/" + fileName + ".json");

            //log.info("Reading JSON file: {}", file.getName());

            JsonNode json = mapper.readTree(file);

            JsonNode value = json.get(key);

            if (value == null) {
                throw new IllegalArgumentException(
                        String.format(
                                "Key '%s' not found in file '%s.json'",
                                key,
                                fileName));
            }

            return value.asText();

        } catch (IOException e) {

            log.error("Unable to read JSON file: {}/{}.json",
                    folder,
                    fileName,
                    e);

            throw new RuntimeException(e);

        } catch (IllegalArgumentException e) {

            log.error(e.getMessage());

            throw e;
        }
    }

    // ==========================================================
    // USER TEST DATA
    // ==========================================================
    public static String getUserData(String key) {
        return readData("testdata", "userSignup", key);
    }

    // ==========================================================
    // CONTACT TEST DATA
    // ==========================================================
    public static String getContactData(String key) {
        return readData("testdata", "addContact", key);
    }

    // ==========================================================
    // UPDATED TEST DATA
    // ==========================================================
    public static String getUpdatedData(String key) {
        return readData("testdata", "updateContact", key);
    }
}