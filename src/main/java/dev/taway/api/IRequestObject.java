package dev.taway.api;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * A object representation of a http request. Used by {@link dev.taway.api.IApiHandler}
 * @since 0.1
 */
public interface IRequestObject {
    /**
     * Gets {@link org.json.simple.JSONObject} with all the headers.
     * @return JSONObject of headers.
     * @since 0.1
     */
    JSONObject getHeaders();

    /***
     * Gets {@link org.json.simple.JSONObject} of body.
     * @return
     * @since 0.1
     */
    JSONObject getBody();

    /**
     * URI that the request is sent to.
     * @return Destination to which this request is sent to.
     * @since 0.1
     */
    String getDestination();

    /**
     * Boolean which determines if the response body should be treated and parsed as JSON.
     * @return A boolean.
     * @since 0.1
     */
    boolean getParseBodyAsJSON();
}
