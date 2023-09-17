package dev.taway.io.json;

import dev.taway.io.file.File;
import dev.taway.logging.LogLevel;
import dev.taway.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Object used for storing, serialising and deserializing JSON data. <br>
 * NOTE: Serialization   = Program -> File<br>
 *       Deserialization = File -> Program<br>
 * Wrapper for {@link org.json.simple}
 * @since 0.1.4
 */
public class JsonObject {
    private static Logger logger = new Logger("JsonObject");
    private JSONObject jsonObject;

    public JsonObject() {}

    public JsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    /**
     * Converts HashMap into a JsonObject
     * @param data Data to be converted.
     * @param clearObject If true then the JsonObject will be cleared before writing into it.
     * @since 0.1.4
     * @see #getJsonObject()
     */
    public void hashMapToJsonObject(HashMap<String, Object> data, boolean clearObject) {
        try {
            if (clearObject) jsonObject = null;
            if (jsonObject == null) {
                jsonObject = new JSONObject();
                for (Map.Entry<String, Object> set : data.entrySet()){
                    jsonObject.put(set.getKey(), set.getValue());
                }
            } else {
                logger.log(LogLevel.ERROR, "hashMapToJsonObject", "Could not convert HashMap to JsonObject as it's not empty and currently contains data.");
            }
        } catch (Exception exception) {
            logger.log(LogLevel.ERROR, "hashMapToJsonObject", exception.getMessage());
        }
    }

    /**
     * Converts string version of JSON and saves it into a JsonObject.
     * @param jsonString Json string to be converted into a JsonObject
     * @param clearObject If true then the JsonObject will be cleared before writing into it.
     * @since 0.1.4
     * @see #getJsonObject()
     */
    public void stringToJsonObject(String jsonString, boolean clearObject) {
        try {
            if (clearObject) jsonObject = null;
            if(jsonObject == null) {
                JSONParser jsonParser = new JSONParser();
                jsonObject = (JSONObject)jsonParser.parse(jsonString);
            } else {
                logger.log(LogLevel.ERROR, "stringToJsonObject", "Could not convert String to JsonObject as it's not empty and currently contains data.");
            }
        } catch (Exception exception) {
            logger.log(LogLevel.ERROR, "stringToJsonObject", exception.getMessage());
        }
    }

    /**
     * Deserializes the file into a JsonObject;
     * @param path Path to JSON file.
     * @param clearObject If the object should be cleared before deserializing into it.
     * @since 0.1.4
     * @see #getJsonObject()
     */
    public void deserializeJsonFromFile(String path, boolean clearObject) {
        try {
            if (clearObject) jsonObject = null;
            if(jsonObject == null) {
                File file = new File(path);
                String jsonString = file.readAllAsString();
                stringToJsonObject(jsonString, true);
            } else {
                logger.log(LogLevel.ERROR, "deserializeJsonFromFile", "Could not convert file to JsonObject as it's not empty and currently contains data.");
            }
        } catch (Exception exception) {
            logger.log(LogLevel.ERROR, "deserializeJsonFromFile", exception.getMessage());
        }
    }

    /**
     * Serializes the JSON object into a file.
     * @param path Path to where the json should be saved to.
     * @param clearObject Determines if the JsonObject should be cleared after writing it into a file.
     * @since 0.1.4
     */
    public void serializeJsonToFile(String path, boolean clearObject) {
        try {
            if(jsonObject != null) {
                File file = new File(path);
                file.create();
                file.overwrite(jsonObject.toJSONString());
            } else {
                logger.log(LogLevel.ERROR, "serializeJsonToFile", "Could not convert JsonObject to file as it's empty and currently contains no data.");
            }
            if (clearObject) jsonObject = null;
        } catch (Exception exception) {
            logger.log(LogLevel.ERROR, "serializeJsonToFile", exception.getMessage());
        }
    }

    /**
     * If the JsonObject exists it returns it otherwise it logs a warn message and returns a new (empty) JsonObject.
     * @return Returns the JsonObject.
     * @since 0.1.4
     */
    public JSONObject getJsonObject() {
        if (jsonObject == null) {
            logger.log(LogLevel.WARN, "getJsonObject", "Returning new JsonObject because the one held is null.");
            return new JSONObject();
        }
        return jsonObject;
    }

    /** @param jsonObject Sets the JsonObject.
     * @since 0.1.4
     */
    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
