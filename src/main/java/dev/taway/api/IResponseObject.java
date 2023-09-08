package dev.taway.api;

import org.json.simple.JSONObject;

/**
 * A object representation of {@link dev.taway.api.ApiHandler} response.
 * @since 0.1
 */
public interface IResponseObject {
    /**
     * @return Original {@link dev.taway.api.IRequestObject} that was used.
     * @since 0.1
     */
    IRequestObject getRequestObject();

    /**
     * @return Headers that arrived as a response.
     * @since 0.1
     */
    JSONObject getHeaders();

    /**
     * If the response was treated as JSON then this object is not empty.
     * @return Parsed body as JSONObject.
     * @since 0.1
     */
    JSONObject getBodyParsed();

    /**
     * Response body as string that was returned.
     * @return Response body as string. (unmodified)
     * @since 0.1
     */
    String getBodyOriginal();

    /**
     * Response status code.
     * @return Status code.
     * @since 0.1
     */
    int getStatusCode();

    /**
     * @return Millis it took to get a reply from the api
     * @since 0.1
     */
    long getResponseTime();
}
