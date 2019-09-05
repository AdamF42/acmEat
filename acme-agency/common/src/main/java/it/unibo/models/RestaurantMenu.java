package it.unibo.models;


import com.google.gson.annotations.Expose;

import java.util.List;

public class RestaurantMenu {

    @Expose
    public String name;

    @Expose
    public List<Dish> menu;

}
