package dev.taway;

import org.json.simple.JSONObject;

public class Main {
    public static void main(String[] args) {
        JSONObject jO = new JSONObject();
        jO.put("one", "one");
        jO.put("two", 2);
        System.out.println(jO.toJSONString());
    }
}