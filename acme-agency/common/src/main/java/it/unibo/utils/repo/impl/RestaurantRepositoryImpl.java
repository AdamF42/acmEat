package it.unibo.utils.repo.impl;

import it.unibo.models.Restaurant;
import it.unibo.utils.repo.DataBase;
import it.unibo.utils.repo.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRepositoryImpl implements RestaurantRepository {

    public List<Restaurant> getRestaurantsByCity(String city) {
        List<Restaurant> result = new ArrayList<Restaurant>();
        for (Restaurant restaurant: DataBase.restaurants) {
            if (restaurant.city.equals(city))
                result.add(restaurant);
        }
        return result;
    }

    public Restaurant getRestaurantByName(String name) {

        for (Restaurant restaurant: DataBase.restaurants) {
            if (restaurant.name.equals(name))
                return restaurant;
        }
        return new Restaurant();
    }
}
