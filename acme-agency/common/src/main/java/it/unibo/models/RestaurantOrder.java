package it.unibo.models;

import com.google.gson.annotations.Expose;

import java.util.List;

public class RestaurantOrder {

    @Expose
    public String restaurant;

    @Expose
    public List<Dish> dishes;

    @Expose
    public String delivery_time;

    @Expose
    public Integer id;

    @Expose
    public Status status;

    @Expose
    public String from;

    @Expose
    public String to;

    @Override
    public String toString() {
        return "RestaurantOrder{" +
                "restaurant='" + restaurant + '\'' +
                ", dishes=" + dishes +
                ", delivery_time='" + delivery_time + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}

