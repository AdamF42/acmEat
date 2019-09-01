package it.unibo.models;

import com.google.gson.annotations.Expose;
import it.unibo.models.entities.Restaurant;
import java.util.ArrayList;
import java.util.List;

public class RestaurantList {

    @Expose
    private ArrayList<Restaurant> restaurants;

    public RestaurantList() {
        this.restaurants = new ArrayList<>();
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = new ArrayList<>(restaurants);
    }

    public boolean isEmpty(){
        return this.restaurants.isEmpty();
    }

    public ArrayList<Restaurant> getRestaurants() {
        return this.restaurants;
    }
}
