package dev.taway.net.api;

import org.json.simple.JSONObject;

/**
 * An object representation of a http request. Used by {@link ApiHandler}
 *
 * @see dev.taway.net.api.ApiHandler#sendRequest(IRequest)
 * @see dev.taway.net.api.ApiHandler#addToQueue(IRequest)
 * @see dev.taway.net.api.RequestType
 * @since 0.1
 */
public class Request implements IRequest {
    JSONObject headers;
    JSONObject body;
    String destination;
    boolean parseBodyAsJSON;
    RequestType requestType;

    public Request(RequestType requestType, JSONObject headers, JSONObject body, String destination) {
        this.requestType = requestType;
        this.headers = headers;
        this.body = body;
        this.destination = destination;
    }

    /**
     * Gets {@link org.json.simple.JSONObject} with all the headers.
     *
     * @return JSONObject of headers.
     * @since 0.1
     */
    @Override
    public JSONObject getHeaders() {
        return headers;
    }

    /***
     * Gets {@link org.json.simple.JSONObject} of body.
     * @return
     * @since 0.1
     */
    @Override
    public JSONObject getBody() {
        return body;
    }

    /**
     * URI that the request is sent to.
     *
     * @return Destination to which this request is sent to.
     * @since 0.1
     */
    @Override
    public String getDestination() {
        return destination;
    }

    /**
     * Boolean which determines if the response body should be treated and parsed as JSON.
     *
     * @return A boolean.
     * @since 0.1
     */
    @Override
    public boolean getParseBodyAsJSON() {
        return parseBodyAsJSON;
    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }
}
