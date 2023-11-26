package dev.taway.net.api;

import dev.taway.exception.net.api.APIHandlerException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class APIHandlerAsync {

    private final ApiHandler apiHandler;

    public APIHandlerAsync(ApiHandler apiHandler) {
        this.apiHandler = apiHandler;
    }

    public CompletableFuture<Response> sendRequestAsync(IRequest requestObject) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return apiHandler.sendRequest(requestObject);
            } catch (IOException | InterruptedException | ParseException | APIHandlerException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Void> executeQueueAsync() {
        return CompletableFuture.runAsync(() -> {
            try {
                apiHandler.executeQueue();
            } catch (IOException | ParseException | InterruptedException | APIHandlerException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Void> addToQueueAsync(IRequest requestObject) {
        return CompletableFuture.runAsync(() -> apiHandler.addToQueue(requestObject));
    }

    public CompletableFuture<Void> removeLastFromQueueAsync() {
        return CompletableFuture.runAsync(apiHandler::removeLastFromQueue);
    }

    public CompletableFuture<Void> removeFirstFromQueueAsync() {
        return CompletableFuture.runAsync(apiHandler::removeFirstFromQueue);
    }

    public CompletableFuture<Void> removeIndexFromQueueAsync(int i) {
        return CompletableFuture.runAsync(() -> apiHandler.removeIndexFromQueue(i));
    }

    public CompletableFuture<ArrayList<IRequest>> getRequestQueueAsync() {
        return CompletableFuture.supplyAsync(apiHandler::getRequestQueue);
    }

    public CompletableFuture<ArrayList<IResponse>> getResponsesToQueueAsync() {
        return CompletableFuture.supplyAsync(apiHandler::getResponsesToQueue);
    }
}
