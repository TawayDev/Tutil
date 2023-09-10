package dev.taway.net.api;

import java.util.ArrayList;

/**
 * A wrapper for {@link java.net.http} with a few extra features like queuing requests and running them all at once.
 * @since 0.1
 */
public interface IApiHandler {
    ResponseObject post(IRequestObject requestObject);
    void executeQueue();
    void addToQueue(IRequestObject requestObject);
    void removeLastFromQueue();
    void removeFirstFromQueue();
    void removeIndexFromQueue(int i);
    ArrayList<IRequestObject> getRequestQueue();
    ArrayList<IResponseObject> getResponsesToQueue();
}
