package dev.taway.tutil.net.api;

import org.json.simple.JSONObject;

/**
 * Object representation of a {@link ApiHandler} response.
 *
 * @see ApiHandler#sendRequest(IRequest)
 * @see ApiHandler#getResponsesToQueue()
 * @since 0.1
 */
public class Response implements IResponse {
    IRequest requestObject;
    JSONObject headers;
    JSONObject bodyParsed;
    String bodyOriginal;
    int statusCode;
    long responseTime;

    boolean treatBodyAsJson;

    public Response(IRequest requestObject, JSONObject headers, JSONObject bodyParsed, String bodyOriginal, int statusCode, long responseTime, boolean treatBodyAsJson) {
        this.requestObject = requestObject;
        this.headers = headers;
        this.bodyParsed = bodyParsed;
        this.bodyOriginal = bodyOriginal;
        this.statusCode = statusCode;
        this.responseTime = responseTime;
        this.treatBodyAsJson = treatBodyAsJson;
    }
//region BoilerPlate

    /**
     * @return Original {@link IRequest} that was used.
     * @since 0.1
     */
    @Override
    public IRequest getRequestObject() {
        return requestObject;
    }

    /**
     * @return Headers that arrived as a response.
     * @since 0.1
     */
    @Override
    public JSONObject getHeaders() {
        return headers;
    }

    /**
     * If the response was treated as JSON then this object is not empty.
     *
     * @return Parsed body as JSONObject.
     * @since 0.1
     */
    @Override
    public JSONObject getBodyParsed() {
        return bodyParsed;
    }

    /**
     * Response body as string that was returned.
     *
     * @return Response body as string. (unmodified)
     * @since 0.1
     */
    @Override
    public String getBody() {
        return bodyOriginal;
    }

    /**
     * Response status code.
     *
     * @return Status code.
     * @since 0.1
     */
    @Override
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @return Millis it took to get a reply from the api
     * @since 0.1
     */
    @Override
    public long getResponseTime() {
        return responseTime;
    }
//endregion
}
