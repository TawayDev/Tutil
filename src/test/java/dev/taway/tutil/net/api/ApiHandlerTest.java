package dev.taway.tutil.net.api;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
class ApiHandlerTest {
    ApiHandler apiHandler = new ApiHandler();
    JSONObject headers = new JSONObject();

    @Test
    public void testGetRequest() throws Exception {
        IRequest getRequest = new Request(RequestType.GET, headers, null, "https://jsonplaceholder.typicode.com/posts");
        Response response = apiHandler.sendRequest(getRequest);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testPostRequest() throws Exception {
        JSONObject postData = new JSONObject();
        postData.put("title", "foo");
        postData.put("body", "bar");
        postData.put("userId", 1);

        IRequest postRequest = new Request(RequestType.POST, headers, postData, "https://jsonplaceholder.typicode.com/posts");
        Response response = apiHandler.sendRequest(postRequest);
        assertEquals(201, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testPutRequest() throws Exception {
        JSONObject putData = new JSONObject();
        putData.put("id", 1);
        putData.put("title", "foo");
        putData.put("body", "bar");
        putData.put("userId", 1);

        IRequest putRequest = new Request(RequestType.PUT, headers, putData, "https://jsonplaceholder.typicode.com/posts/1");
        Response response = apiHandler.sendRequest(putRequest);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDeleteRequest() throws Exception {
        IRequest deleteRequest = new Request(RequestType.DELETE, headers, null, "https://jsonplaceholder.typicode.com/posts/1");
        Response response = apiHandler.sendRequest(deleteRequest);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testPatchRequest() throws Exception {
        JSONObject patchData = new JSONObject();
        patchData.put("title", "foo");

        IRequest patchRequest = new Request(RequestType.PATCH, headers, patchData, "https://jsonplaceholder.typicode.com/posts/1");
        Response response = apiHandler.sendRequest(patchRequest);
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    //    Note: Head, Options, and Trace methods are not typically used to fetch resource content.
//    They are more for retrieving metadata, so a successful status code might be enough for a test.
    @Test
    public void testHeadRequest() throws Exception {
        IRequest headRequest = new Request(RequestType.HEAD, headers, null, "https://jsonplaceholder.typicode.com/posts");
        Response response = apiHandler.sendRequest(headRequest);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testOptionsRequest() throws Exception {
        IRequest optionsRequest = new Request(RequestType.OPTIONS, headers, null, "https://jsonplaceholder.typicode.com/entries");
        Response response = apiHandler.sendRequest(optionsRequest);
        assertEquals(204, response.getStatusCode());
    }

    @Test
    public void testTraceRequest() throws Exception {
        IRequest traceRequest = new Request(RequestType.TRACE, headers, null, "https://jsonplaceholder.typicode.com/entries");
        Response response = apiHandler.sendRequest(traceRequest);
        assertEquals(405, response.getStatusCode());
    }
}