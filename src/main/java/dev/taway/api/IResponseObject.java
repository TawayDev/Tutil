package dev.taway.api;

import org.json.simple.JSONObject;

public interface IResponseObject {

    IRequestObject getRequestObject();

    JSONObject getHeaders();

    JSONObject getBodyParsed();

    String getBodyOriginal();

    int getStatusCode();

    double getResponseTime();
}
