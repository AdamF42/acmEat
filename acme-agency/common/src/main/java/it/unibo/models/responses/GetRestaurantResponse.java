package it.unibo.models.responses;

import com.google.gson.annotations.Expose;
import it.unibo.models.Result;
import it.unibo.models.entities.Restaurant;
import java.util.List;

public class GetRestaurantResponse {

    @Expose
    private List<Restaurant> restaurants;

    @Expose
    private Result result;

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
    public void setResult(Result result){this.result=result;}
    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
