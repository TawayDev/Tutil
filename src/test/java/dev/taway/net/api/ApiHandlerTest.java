package dev.taway.net.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("API Key and Destination not provided due to security reasons.")
class ApiHandlerTest {
    ApiHandler apiHandler = new ApiHandler();

    //    FOR SECURITY REASONS DELETED INFO! (trust me it works 😎)
    String key = "";
    String destination = "";

    RequestObject getRequestObject() {
        JSONObject headers = new JSONObject();
        headers.put("KEY", key);
        JSONObject body = new JSONObject();
        body.put("KEY_TYPE", "API");
        body.put("ACTION", "LOGIN");
        body.put("IP", "127.0.0.1,19687");
        body.put("USERNAME", "MysteriousTaway");
        body.put("UUID", "649266d7-bd82-4952-bd40-03e000c79e8e");
        body.put("OP", true);
        return new RequestObject(headers, body, destination);
    }

    @Test
    void post() throws IOException, ParseException, InterruptedException {
        ResponseObject responseObject = apiHandler.post(getRequestObject());
        assertEquals("{\"STATUS\":\"OK\",\"BANNED\":false,\"REASON\":\"none\",\"BAN_DATE\":\"none\"}", responseObject.bodyOriginal);
    }

    @Test
    void executeQueue() throws IOException, ParseException, InterruptedException {
        apiHandler.addToQueue(getRequestObject());
        apiHandler.addToQueue(getRequestObject());
        apiHandler.executeQueue();
        ArrayList<IResponseObject> responses = apiHandler.getResponsesToQueue();
        String responseString = responses.get(0).getBodyOriginal() + responses.get(1).getBodyOriginal();
        assertEquals("{\"STATUS\":\"OK\",\"BANNED\":false,\"REASON\":\"none\",\"BAN_DATE\":\"none\"}{\"STATUS\":\"OK\",\"BANNED\":false,\"REASON\":\"none\",\"BAN_DATE\":\"none\"}", responseString);
    }
}