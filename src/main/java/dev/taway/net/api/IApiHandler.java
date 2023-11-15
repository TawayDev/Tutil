package dev.taway.net.api;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A wrapper for {@link java.net.http} with a few extra features like queuing requests and running them all at once.
 *
 * @since 0.1
 */
public interface IApiHandler {
    ResponseObject post(IRequestObject requestObject) throws IOException, InterruptedException, ParseException;

    void executeQueue() throws IOException, ParseException, InterruptedException;

    void addToQueue(IRequestObject requestObject);

    void removeLastFromQueue();

    void removeFirstFromQueue();

    void removeIndexFromQueue(int i);

    ArrayList<IRequestObject> getRequestQueue();

    ArrayList<IResponseObject> getResponsesToQueue();
}
