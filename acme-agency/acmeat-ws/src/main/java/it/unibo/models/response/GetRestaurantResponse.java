package it.unibo.models.response;

import com.google.gson.annotations.Expose;
import it.unibo.models.RestaurantList;
import it.unibo.models.Result;
import it.unibo.models.entities.Restaurant;
import java.util.List;


public class GetRestaurantResponse implements Response {

    @Expose
    private List<Restaurant> restaurants;

    @Expose
    private Result result = SuccessResult.getResult();


    public GetRestaurantResponse(RestaurantList restaurants) {
        this.restaurants = restaurants.getRestaurants();
    }
}

