package dev.taway.tutil.net.api;

import dev.taway.tutil.exception.net.api.APIHandlerException;
import dev.taway.tutil.logging.LogLevel;
import dev.taway.tutil.logging.Logger;
import dev.taway.tutil.time.Stopwatch;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Getter
@Setter
public class ApiHandler implements IApiHandler {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final Logger logger = new Logger("ApiHandler");
    ArrayList<IRequest> requestQueue = new ArrayList<>();
    ArrayList<IResponse> responsesToQueue = new ArrayList<>();
    long totalQueueMillis;

    /**
     * Sends a request to an API endpoint and returns a response.
     *
     * @param requestObject Object representation of a request.
     * @return Returns object representation of the response.
     * @see Request Request
     * @see Response Response
     * @since 0.1.5
     */
    @Override
    public Response sendRequest(IRequest requestObject) throws IOException, InterruptedException, ParseException, APIHandlerException {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(requestObject.getDestination()));
        JSONObject headers = requestObject.getHeaders();
        headers.forEach((key, value) -> requestBuilder.header((String) key, (String) value));

        configureRequestType(requestBuilder, requestObject);

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject responseJsonObject = new JSONObject();
        if (requestObject.getParseBodyAsJSON()) {
            JSONParser jsonParser = new JSONParser();
            responseJsonObject = (JSONObject) jsonParser.parse(response.body());
        }
        JSONObject jsonHeaders = new JSONObject();
        jsonHeaders.putAll(response.headers().map());
        stopwatch.stop();
        return new Response(
                requestObject,
                jsonHeaders,
                responseJsonObject,
                response.body(),
                response.statusCode(),
                stopwatch.getElapsedMillis(),
                requestObject.getParseBodyAsJSON()
        );
    }

    private void configureRequestType(HttpRequest.Builder requestBuilder, IRequest requestObject) throws APIHandlerException {
        switch (requestObject.getRequestType()) {
            case GET -> requestBuilder.GET();
            case POST ->
                    requestBuilder.POST(HttpRequest.BodyPublishers.ofString(requestObject.getBody().toJSONString()));
            case PUT -> requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(requestObject.getBody().toJSONString()));
            case DELETE -> requestBuilder.DELETE();
            case PATCH ->
                    requestBuilder.method("PATCH", HttpRequest.BodyPublishers.ofString(requestObject.getBody().toJSONString()));
            case HEAD -> requestBuilder.method("HEAD", HttpRequest.BodyPublishers.noBody());
            case OPTIONS -> requestBuilder.method("OPTIONS", HttpRequest.BodyPublishers.noBody());
            case TRACE -> requestBuilder.method("TRACE", HttpRequest.BodyPublishers.noBody());
            default -> throw new APIHandlerException("Unsupported request method: " + requestObject.getRequestType());
        }
    }

    /**
     * Runs entire {@link IRequest} Queue one by one and stores their responses in ResponsesToQueue ArrayList
     *
     * @version 0.1.2
     * @see #addToQueue(IRequest)
     * @since 0.1
     */
    @Override
    public void executeQueue() throws IOException, ParseException, InterruptedException, APIHandlerException {
        if (requestQueue.isEmpty()) {
            logger.log(LogLevel.WARN, "executeQueue", "Request queue is empty!");
            return;
        }
        Stopwatch stopwatch = new Stopwatch();
        for (IRequest requestObject : requestQueue) {
            responsesToQueue.add(sendRequest(requestObject));
        }
        requestQueue.clear();
        stopwatch.stop();
        totalQueueMillis = stopwatch.getElapsedMillis();
    }

    /**
     * Adds {@link IRequest} to RequestQueue.
     *
     * @param requestObject Object to be added.
     * @see Request Request
     * @since 0.1
     */
    @Override
    public void addToQueue(IRequest requestObject) {
        requestQueue.add(requestObject);
    }

    /**
     * Removes last {@link IRequest} from RequestQueue.
     *
     * @since 0.1
     */
    @Override
    public void removeLastFromQueue() {
        requestQueue.remove(requestQueue.size() - 1);
    }

    /**
     * Removes first Request from RequestQueue.
     *
     * @since 0.1
     */
    @Override
    public void removeFirstFromQueue() {
        requestQueue.remove(0);
    }

    /**
     * Removes {@link IRequest} from queue at position i.
     *
     * @param i Position in array.
     * @since 0.1
     */
    @Override
    public void removeIndexFromQueue(int i) {
        requestQueue.remove(i);
    }

    /**
     * @return Returns RequestQueue
     * @since 0.1
     */
    @Override
    public ArrayList<IRequest> getRequestQueue() {
        return requestQueue;
    }

    /**
     * @return Returns ArrayList of responses to RequestQueue
     * @since 0.1
     */
    @Override
    public ArrayList<IResponse> getResponsesToQueue() {
        return responsesToQueue;
    }
}
