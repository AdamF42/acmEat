package it.unibo.models;


import com.google.gson.annotations.Expose;

public class RestaurantAvailability {

    @Expose
    public String name;

    @Expose
    public Boolean is_available;
}
