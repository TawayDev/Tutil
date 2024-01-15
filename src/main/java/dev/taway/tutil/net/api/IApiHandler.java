package dev.taway.tutil.net.api;

import dev.taway.tutil.exception.net.api.APIHandlerException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A wrapper for {@link java.net.http} with a few extra features like queuing requests and running them all at once.
 *
 * @since 0.1
 */
public interface IApiHandler {
//    Response post(IRequest requestObject) throws IOException, InterruptedException, ParseException, APIHandlerException;

    Response sendRequest(IRequest requestObject) throws IOException, InterruptedException, ParseException, APIHandlerException;

    void executeQueue() throws IOException, ParseException, InterruptedException, APIHandlerException;

    void addToQueue(IRequest requestObject);

    void removeLastFromQueue();

    void removeFirstFromQueue();

    void removeIndexFromQueue(int i);

    ArrayList<IRequest> getRequestQueue();

    ArrayList<IResponse> getResponsesToQueue();
}
