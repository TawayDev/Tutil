package dev.taway.api;

import org.json.simple.JSONObject;
public class ResponseObject implements IResponseObject {
    IRequestObject requestObject;
    JSONObject headers;
    JSONObject bodyParsed;
    String bodyOriginal;
    int statusCode;
    long responseTime;

    boolean treatBodyAsJson;

    public ResponseObject(IRequestObject requestObject, JSONObject headers, JSONObject bodyParsed, String bodyOriginal, int statusCode, long responseTime, boolean treatBodyAsJson) {
        this.requestObject = requestObject;
        this.headers = headers;
        this.bodyParsed = bodyParsed;
        this.bodyOriginal = bodyOriginal;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.treatBodyAsJson = treatBodyAsJson;
    }

    @Override
    public IRequestObject getRequestObject() {
        return requestObject;
    }
    @Override
    public JSONObject getHeaders() {
        return headers;
    }
    @Override
    public JSONObject getBodyParsed() {
        return bodyParsed;
    }
    @Override
    public String getBodyOriginal() {
        return bodyOriginal;
    }
    @Override
    public int getStatusCode() {
        return statusCode;
    }
    @Override
    public long getResponseTime() {
        return responseTime;
    }
}
