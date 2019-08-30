package it.unibo.models.entities;

import com.google.gson.annotations.Expose;
import it.unibo.models.Dish;

import java.util.List;

public class Restaurant {

    @Expose
    public String name;

    @Expose
    public List<Dish> menu;

    @Expose
    public String city;

    @Expose
    public String url;

    public Restaurant(){}

    public Restaurant(String name, List<Dish> menu, String city, String url) {
        this.name = name;
        this.menu = menu;
        this.city = city;
        this.url = url;
    }
}
