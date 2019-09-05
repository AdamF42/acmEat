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

    public Double calculateTotalPrice(){

        return dishes
                .stream()
                .mapToDouble(f -> Double.parseDouble(f.getPrice()))
                .sum();
    }
}

