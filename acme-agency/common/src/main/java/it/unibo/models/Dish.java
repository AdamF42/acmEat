package it.unibo.models;

import com.google.gson.annotations.Expose;

public class Dish {

    @Expose
    public String name;

    @Expose
    public String price;

    public Dish() { }

    public Dish(String name, String price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}