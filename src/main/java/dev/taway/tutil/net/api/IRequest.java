package dev.taway.tutil.net.api;

import org.json.simple.JSONObject;

public interface IRequest {
    JSONObject getHeaders();

    JSONObject getBody();

    String getDestination();

    boolean getParseBodyAsJSON();

    RequestType getRequestType();
}
