package it.unibo.models;

import com.google.gson.annotations.Expose;
import it.unibo.models.entities.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantList {

    @Expose
    private List<Restaurant> restaurants;

    public RestaurantList() {
        this.restaurants = new ArrayList<>();
    }


    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public boolean isEmpty(){
        return this.restaurants.isEmpty();
    }

    public List<Restaurant> getRestaurants() {
        return this.restaurants;
    }
}
