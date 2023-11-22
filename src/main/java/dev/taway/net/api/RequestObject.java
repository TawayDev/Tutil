package dev.taway.net.api;

import lombok.Getter;
import org.json.simple.JSONObject;

/**
 * An object representation of a http request. Used by {@link ApiHandler}
 *
 * @see dev.taway.net.api.ApiHandler#post(IRequestObject)
 * @see dev.taway.net.api.ApiHandler#addToQueue(IRequestObject)
 * @since 0.1
 */
@Getter
public class RequestObject implements IRequestObject {
    JSONObject headers;
    JSONObject body;
    String destination;
    boolean parseBodyAsJSON;

    public RequestObject(JSONObject headers, JSONObject body, String destination) {
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
}
