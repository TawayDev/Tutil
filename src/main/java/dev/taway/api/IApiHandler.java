package dev.taway.api;

import java.util.ArrayList;

/**
 * A wrapper for {@link java.net.http} with a few extra features like queuing requests and running them all at once.
 * @since 0.1
 */
public interface IApiHandler {
    /**
     * Sends RequestObject to api endpoint and returns ResponseObject.
     * @param requestObject Object representation of a request.
     * @return Returns object representation of the response.
     * @since 0.1
     */
    ResponseObject post(IRequestObject requestObject);

    /**
     * Runs entire {@link dev.taway.api.IRequestObject} Queue one by one and stores their responses in ResponsesToQueue ArrayList
     * @since 0.1
     */
    void executeQueue();

    /**
     * Adds {@link dev.taway.api.IRequestObject} to RequestQueue.
     * @param requestObject Object to be added.
     * @since 0.1
     */
    void addToQueue(IRequestObject requestObject);

    /**
     * Removes last {@link dev.taway.api.IRequestObject} from RequestQueue.
     * @since 0.1
     */
    void removeLastFromQueue();

    /**
     * Removes first RequestObject from RequestQueue.
     * @since 0.1
     */
    void removeFirstFromQueue();

    /**
     * Removes {@link dev.taway.api.IRequestObject} from queue at position i.
     * @param i Position in array.
     * @since 0.1
     */
    void removeIndexFromQueue(int i);

    /**
     * @return Returns RequestQueue
     * @since 0.1
     */
    ArrayList<IRequestObject> getRequestQueue();

    /**
     * @return Returns ArrayList of responses to RequestQueue
     * @since 0.1
     */
    ArrayList<IResponseObject> getResponsesToQueue();
}
