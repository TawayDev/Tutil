package dev.taway.net.api;

import dev.taway.exception.net.api.APIHandlerException;
import dev.taway.logging.LogLevel;
import dev.taway.logging.Logger;
import dev.taway.time.Stopwatch;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ApiHandler implements IApiHandler {
    private static final Logger logger = new Logger("ApiHandler");
    ArrayList<IRequest> requestQueue = new ArrayList<>();
    ArrayList<IResponse> responsesToQueue = new ArrayList<>();
    long totalQueueMillis;

//    /**
//     * -- DEPRECATED --
//     * Sends a request to an API endpoint and returns a response.
//     * Supports ONLY POST request!
//     *
//     * @param requestObject Object representation of a request.
//     * @return Returns object representation of the response.
//     * @see Request Request
//     * @see Response Response
//     * @since 0.1
//     */
//    @Override
//    @Deprecated
//    public Response post(IRequest requestObject) throws IOException, InterruptedException, ParseException, APIHandlerException {
//        if (requestObject.getRequestType() != RequestType.POST)
//            throw new APIHandlerException("Cannot send a POST request for a request of type " + requestObject.getRequestType());
//        Stopwatch stopwatch = new Stopwatch();
//        stopwatch.start();
////            Create request:
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest.Builder r = HttpRequest.newBuilder()
//                .uri(URI.create(requestObject.getDestination()))
//                .POST(HttpRequest.BodyPublishers.ofString(requestObject.getBody().toJSONString()));
////            Get headers
//        Iterator<String> keys = requestObject.getHeaders().keySet().iterator();
//        JSONObject headers = requestObject.getHeaders();
//        while (keys.hasNext()) {
//            String key = keys.next();
////                I have a bad feeling that casting everything to string here will cause some endpoint issues but java has forced my hand once again :)
//            r.header(key, String.valueOf(headers.get(key)));
//        }
////            Build request:
//        HttpRequest request = r.build();
////            Send and receive:
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
////            If the body should be treated as JSON then parse it:
//        JSONParser jsonParser = new JSONParser();
//        JSONObject responseJsonObject = new JSONObject();
//        if (requestObject.getParseBodyAsJSON()) {
//            responseJsonObject = (JSONObject) jsonParser.parse(response.body());
//        }
////            Convert returned headers to
//        JSONObject jsonHeaders = new JSONObject();
//        jsonHeaders.putAll(response.headers().map());
////            Stop time
//        stopwatch.stop();
////            Return
//        return new Response(
//                requestObject,
//                jsonHeaders,
//                responseJsonObject,
//                response.body(),
//                response.statusCode(),
//                stopwatch.getElapsedMillis(),
//                requestObject.getParseBodyAsJSON()
//        );
//    }

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

        switch (requestObject.getRequestType()) {
            case GET -> requestBuilder.GET();
            case POST ->
                    requestBuilder.POST(HttpRequest.BodyPublishers.ofString(requestObject.getBody().toJSONString()));
            case PUT -> requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(requestObject.getBody().toJSONString()));
            case DELETE -> requestBuilder.DELETE();
//            Note: PATCH method might not be supported directly and may need to be configured manually
            case PATCH ->
                    requestBuilder.method("PATCH", HttpRequest.BodyPublishers.ofString(requestObject.getBody().toJSONString()));
            case HEAD -> requestBuilder.method("HEAD", HttpRequest.BodyPublishers.noBody());
            case OPTIONS -> requestBuilder.method("OPTIONS", HttpRequest.BodyPublishers.noBody());
            case TRACE -> requestBuilder.method("TRACE", HttpRequest.BodyPublishers.noBody());
            default -> throw new APIHandlerException("Unsupported request method: " + requestObject.getRequestType());
        }

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
