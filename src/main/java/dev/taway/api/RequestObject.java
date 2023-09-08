package dev.taway.api;

import org.json.simple.JSONObject;

import java.util.Iterator;

public class RequestObject implements IRequestObject{
    JSONObject headers;
    JSONObject body;
    String destination;
    boolean parseBodyAsJSON;
    public RequestObject(JSONObject headers, JSONObject body, String destination) {
        this.headers = headers;
        this.body = body;
        this.destination = destination;
    }

    @Override
    public JSONObject getHeaders() {
        return headers;
    }
    @Override
    public JSONObject getBody() {
        return body;
    }
    @Override
    public String getDestination() {
        return destination;
    }
    @Override
    public boolean getParseBodyAsJSON() {
        return parseBodyAsJSON;
    }
}
