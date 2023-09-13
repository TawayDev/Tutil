package dev.taway.net.api;

import dev.taway.logging.LogLevel;
import dev.taway.logging.Logger;
import dev.taway.time.Stopwatch;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;

public class ApiHandler implements IApiHandler{
    ArrayList<IRequestObject> requestQueue = new ArrayList<>();
    ArrayList<IResponseObject> responsesToQueue = new ArrayList<>();
    long totalQueueMillis;
    static Logger logger = new Logger("ApiHandler");

    /**
     * Sends RequestObject to api endpoint and returns ResponseObject.
     * @param requestObject Object representation of a request.
     * @return Returns object representation of the response.
     * @since 0.1
     * @see dev.taway.net.api.RequestObject RequestObject
     * @see dev.taway.net.api.ResponseObject ResponseObject
     */
    @Override
    public ResponseObject post(IRequestObject requestObject) {
        try {
            Stopwatch stopwatch = new Stopwatch();
            stopwatch.start();
//            Create request:
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest.Builder r = HttpRequest.newBuilder()
                    .uri(URI.create(requestObject.getDestination()))
                    .POST(HttpRequest.BodyPublishers.ofString(requestObject.getBody().toJSONString()));
//            Get headers
            Iterator<String> keys = requestObject.getHeaders().keySet().iterator();
            JSONObject headers = requestObject.getHeaders();
            while(keys.hasNext()) {
                String key = keys.next();
//                I have a bad feeling that casting everything to string here will cause some endpoint issues but java has forced my hand once again :)
                r.header(key, String.valueOf(headers.get(key)));
            }
//            Build request:
            HttpRequest request = r.build();
//            Send and receive:
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//            If the body should be treated as JSON then parse it:
            JSONParser jsonParser = new JSONParser();
            JSONObject responseJsonObject = new JSONObject();
            if (requestObject.getParseBodyAsJSON()) {
                responseJsonObject = (JSONObject) jsonParser.parse(response.body());
            }
//            Convert returned headers to
            JSONObject jsonHeaders = new JSONObject();
            jsonHeaders.putAll(response.headers().map());
//            Stop time
            stopwatch.stop();
//            Return
            return new ResponseObject(requestObject, jsonHeaders, responseJsonObject, response.body(), response.statusCode(), stopwatch.getElapsedMillis(), requestObject.getParseBodyAsJSON());
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "post", exception.getMessage());
            return null;
        }
    }
    /**
     * Runs entire {@link IRequestObject} Queue one by one and stores their responses in ResponsesToQueue ArrayList
     * @version 0.1.2
     * @since 0.1
     * @see #addToQueue(IRequestObject)
     */
    @Override
    public void executeQueue() {
        if (requestQueue.size() == 0) {
            logger.log(LogLevel.WARN, "executeQueue", "Request queue is empty!");
            return;
        }
        Stopwatch stopwatch = new Stopwatch();
        for (IRequestObject requestObject : requestQueue) {
            responsesToQueue.add(post(requestObject));
        }
        requestQueue.clear();
        stopwatch.stop();
        totalQueueMillis = stopwatch.getElapsedMillis();
    }
    /**
     * Adds {@link IRequestObject} to RequestQueue.
     * @param requestObject Object to be added.
     * @since 0.1
     * @see dev.taway.net.api.RequestObject RequestObject
     */
    @Override
    public void addToQueue(IRequestObject requestObject) {
        requestQueue.add(requestObject);
    }
    /**
     * Removes last {@link IRequestObject} from RequestQueue.
     * @since 0.1
     */
    @Override
    public void removeLastFromQueue() {
        requestQueue.remove(requestQueue.size() -1);
    }
    /**
     * Removes first RequestObject from RequestQueue.
     * @since 0.1
     */
    @Override
    public void removeFirstFromQueue() {
        requestQueue.remove(0);
    }
    /**
     * Removes {@link IRequestObject} from queue at position i.
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
    public ArrayList<IRequestObject> getRequestQueue() {
        return requestQueue;
    }
    /**
     * @return Returns ArrayList of responses to RequestQueue
     * @since 0.1
     */
    @Override
    public ArrayList<IResponseObject> getResponsesToQueue() {
        return responsesToQueue;
    }
}
