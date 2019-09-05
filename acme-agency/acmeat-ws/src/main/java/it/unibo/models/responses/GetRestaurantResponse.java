package it.unibo.models.responses;

import com.google.gson.annotations.Expose;
import it.unibo.models.RestaurantList;
import it.unibo.models.Result;
import it.unibo.models.entities.Restaurant;

import java.util.List;


public class GetRestaurantResponse implements Response {

    @Expose
    private List<Restaurant> restaurants;

    @Expose
    private Result result;

    public GetRestaurantResponse(RestaurantList restaurants, Result result) {
        this.restaurants = restaurants.getRestaurants();
        this.result = result;
    }
}

