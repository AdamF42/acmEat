package it.unibo.app.fakes;


import org.json.simple.JSONObject;


public class fakeRestaurantOrder {

    public static JSONObject orderOne(){
        JSONObject order1 = new JSONObject();

        // construct content
        String[] content = {"pizza", "carbonara"};
        order1.put("content", content);
        order1.put("delivery_time", "13");
        return order1;
    }



}