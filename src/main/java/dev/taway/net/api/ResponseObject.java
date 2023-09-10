package dev.taway.net.api;

import org.json.simple.JSONObject;
/**
 * Object representation of a {@link ApiHandler} response.
 * @since 0.1
 * @see dev.taway.net.api.ApiHandler#post(IRequestObject)
 * @see ApiHandler#getResponsesToQueue()
 */
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
    /**
     * @return Original {@link IRequestObject} that was used.
     * @since 0.1
     */
    @Override
    public IRequestObject getRequestObject() {
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
     * @return Parsed body as JSONObject.
     * @since 0.1
     */
    @Override
    public JSONObject getBodyParsed() {
        return bodyParsed;
    }
    /**
     * Response body as string that was returned.
     * @return Response body as string. (unmodified)
     * @since 0.1
     */
    @Override
    public String getBodyOriginal() {
        return bodyOriginal;
    }
    /**
     * Response status code.
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
}
