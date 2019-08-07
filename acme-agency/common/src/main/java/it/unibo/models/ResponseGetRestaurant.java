package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ResponseGetRestaurant {

    private List<Restaurant> restaurants;
    private Result result;

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
    public void setResult(Result result){this.result=result;}
    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
