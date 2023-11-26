package dev.taway.net.api;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class APIHandlerAsyncTest {

    APIHandlerAsync apiHandlerAsync = new APIHandlerAsync(new ApiHandler());
    JSONObject headers = new JSONObject();

    @Test
    public void testGetRequestAsync() {
        IRequest getRequest = new Request(RequestType.GET, headers, null, "https://jsonplaceholder.typicode.com/posts");
        apiHandlerAsync.sendRequestAsync(getRequest).thenAccept(response -> {
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getBody());
        }).join();
    }

    @Test
    public void testPostRequestAsync() {
        JSONObject postData = new JSONObject();
        postData.put("title", "foo");
        postData.put("body", "bar");
        postData.put("userId", 1);

        IRequest postRequest = new Request(RequestType.POST, headers, postData, "https://jsonplaceholder.typicode.com/posts");
        apiHandlerAsync.sendRequestAsync(postRequest).thenAccept(response -> {
            assertEquals(201, response.getStatusCode());
            assertNotNull(response.getBody());
        }).join();
    }

    @Test
    public void testPutRequestAsync() {
        JSONObject putData = new JSONObject();
        putData.put("id", 1);
        putData.put("title", "foo");
        putData.put("body", "bar");
        putData.put("userId", 1);

        IRequest putRequest = new Request(RequestType.PUT, headers, putData, "https://jsonplaceholder.typicode.com/posts/1");
        apiHandlerAsync.sendRequestAsync(putRequest).thenAccept(response -> {
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getBody());
        }).join();
    }

    @Test
    public void testDeleteRequestAsync() {
        IRequest deleteRequest = new Request(RequestType.DELETE, headers, null, "https://jsonplaceholder.typicode.com/posts/1");
        apiHandlerAsync.sendRequestAsync(deleteRequest).thenAccept(response -> {
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getBody());
        }).join();
    }

    @Test
    public void testPatchRequestAsync() {
        JSONObject patchData = new JSONObject();
        patchData.put("title", "foo");

        IRequest patchRequest = new Request(RequestType.PATCH, headers, patchData, "https://jsonplaceholder.typicode.com/posts/1");
        apiHandlerAsync.sendRequestAsync(patchRequest).thenAccept(response -> {
            assertEquals(200, response.getStatusCode());
            assertNotNull(response.getBody());
        }).join();
    }

    @Test
    public void testHeadRequestAsync() {
        IRequest headRequest = new Request(RequestType.HEAD, headers, null, "https://jsonplaceholder.typicode.com/posts");
        apiHandlerAsync.sendRequestAsync(headRequest).thenAccept(response -> assertEquals(200, response.getStatusCode())).join();
    }

    @Test
    public void testOptionsRequestAsync() {
        IRequest optionsRequest = new Request(RequestType.OPTIONS, headers, null, "https://jsonplaceholder.typicode.com/posts");
        apiHandlerAsync.sendRequestAsync(optionsRequest).thenAccept(response -> assertEquals(204, response.getStatusCode())).join();
    }

    @Test
    public void testTraceRequestAsync() {
        IRequest traceRequest = new Request(RequestType.TRACE, headers, null, "https://jsonplaceholder.typicode.com/posts");
        apiHandlerAsync.sendRequestAsync(traceRequest).thenAccept(response -> assertEquals(405, response.getStatusCode())).join();
    }
}
