package dev.taway.api;

import dev.taway.other.Stopwatch;
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
            // TODO: logging
            System.out.println(exception.toString());
            return null;
        }
    }

    public void executeQueue() {
        Stopwatch stopwatch = new Stopwatch();
        for (IRequestObject requestObject : requestQueue) {
            responsesToQueue.add(post(requestObject));
        }
        requestQueue.clear();
        stopwatch.stop();
        totalQueueMillis = stopwatch.getElapsedMillis();
    }

    public void addToQueue(IRequestObject requestObject) {
        requestQueue.add(requestObject);
    }

    public void removeLastFromQueue() {
        requestQueue.remove(requestQueue.size() -1);
    }

    public void removeFirstFromQueue() {
        requestQueue.remove(0);
    }

    public void removeIndexFromQueue(int i) {
        requestQueue.remove(i);
    }

    public ArrayList<IRequestObject> getRequestQueue() {
        return requestQueue;
    }

    public ArrayList<IResponseObject> getResponsesToQueue() {
        return responsesToQueue;
    }
}
