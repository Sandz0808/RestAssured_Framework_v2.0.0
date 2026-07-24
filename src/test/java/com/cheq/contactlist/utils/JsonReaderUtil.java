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


    // HELPER FOR JASON FILE THAT NOT IN LIST
    public static String getData(String fileName, String key) {
        return readData("testdata", fileName, key);
    }

    // HELPER FOR JASON FILE IN LIST USING INDEX
    public static String getDatalist(String fileName, int index,  String key) {
        return readListData("testdata",fileName, index, key);
    }


    // HELPER FOR JASON FILE IN NODE
    public static String getDataNOde(String fileName, String objectName,  String key) {
        return readDataNode("testdata", fileName, objectName, key);
    }




    // Json Reader that handle json not in list
    public static String readData(String folder,
                                  String fileName,
                                  String key) {

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



    // Json Reader that handle json file in List
    public static String readListData(
            String folder,
            String fileName,
            int index,
            String key) {

        try {

            File file = new File(
                    "src/test/resources/" + folder + "/" + fileName + ".json");

            // log.info("Reading JSON file: {}", file.getName());

            JsonNode json = mapper.readTree(file);

            if (!json.isArray()) {

                throw new IllegalArgumentException(
                        String.format(
                                "File '%s.json' is not a JSON array.",
                                fileName));

            }

            if (index < 0 || index >= json.size()) {

                throw new IllegalArgumentException(
                        String.format(
                                "Index '%d' is out of bounds for file '%s.json'.",
                                index,
                                fileName));

            }

            JsonNode value = json.get(index).get(key);

            if (value == null) {

                throw new IllegalArgumentException(
                        String.format(
                                "Key '%s' not found at index '%d' in file '%s.json'.",
                                key,
                                index,
                                fileName));

            }

            return value.asText();

        } catch (IOException e) {

            log.error(
                    "Unable to read JSON file: {}/{}.json",
                    folder,
                    fileName,
                    e);

            throw new RuntimeException(e);

        } catch (IllegalArgumentException e) {

            log.error(e.getMessage());

            throw e;
        }

    }

    public static String readDataNode(
            String folder,
            String fileName,
            String objectName,
            String key) {

        try {

            File file = new File(
                    "src/test/resources/" + folder + "/" + fileName + ".json");

            JsonNode json = mapper.readTree(file);

            JsonNode object = json.get(objectName);

            if (object == null) {

                throw new IllegalArgumentException(
                        String.format(
                                "Object '%s' not found in file '%s.json'.",
                                objectName,
                                fileName));

            }

            JsonNode value = object.get(key);

            if (value == null) {

                throw new IllegalArgumentException(
                        String.format(
                                "Key '%s' not found inside object '%s' in file '%s.json'.",
                                key,
                                objectName,
                                fileName));

            }

            return value.asText();

        } catch (IOException e) {

            log.error(
                    "Unable to read JSON file: {}/{}.json",
                    folder,
                    fileName,
                    e);

            throw new RuntimeException(e);

        } catch (IllegalArgumentException e) {

            log.error(e.getMessage());

            throw e;
        }
    }


}