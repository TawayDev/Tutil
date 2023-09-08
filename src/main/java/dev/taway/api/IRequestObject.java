package dev.taway.api;

import org.json.simple.JSONObject;

import java.util.HashMap;

public interface IRequestObject {

    JSONObject getHeaders();

    JSONObject getBody();

    String getDestination();

    boolean getParseBodyAsJSON();
}
