package dev.taway.net.api;

import org.json.simple.JSONObject;

public interface IRequestObject {
    JSONObject getHeaders();

    JSONObject getBody();

    String getDestination();

    boolean getParseBodyAsJSON();
}
