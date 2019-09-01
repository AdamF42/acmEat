package it.unibo.utils;

public class Dish {


    public String name;


    public String price;

    public Dish() {
    }

    public Dish(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}