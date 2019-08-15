package it.unibo.utils.repo;

import it.unibo.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRepositoryImpl implements RestaurantRepository {

    public List<Restaurant> getRestaurantByCity(String city) {
        List<Restaurant> result = new ArrayList<Restaurant>();
        for (Restaurant restaurant: DataBase.restaurants) {
            if (restaurant.city.equals(city))
                result.add(restaurant);
        }
        return result;
    }
}
