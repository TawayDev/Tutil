package dev.taway.net.api;

import org.json.simple.JSONObject;


public interface IResponse {
    IRequest getRequestObject();

    JSONObject getHeaders();

    JSONObject getBodyParsed();

    String getBody();

    int getStatusCode();

    long getResponseTime();
}
