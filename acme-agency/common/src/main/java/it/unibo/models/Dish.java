package it.unibo.models;

public class Dish {


    public String name;


    public String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Dish() { }

    public Dish(String name, String price) {
        this.name = name;
        this.price = price;
    }
}