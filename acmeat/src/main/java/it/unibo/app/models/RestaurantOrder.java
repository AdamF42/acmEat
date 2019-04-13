package it.unibo.app.models;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class RestaurantOrder {

    public String[] getContent() {
        return content;
    }

    @JsonProperty
    private String[] content;

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    @JsonProperty
    private int id;

    @JsonProperty
    private String status;

    @JsonProperty
    private String delivery_time;

}
