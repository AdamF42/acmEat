package it.unibo.utils.repo;

import it.unibo.models.entities.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> getRestaurantsByCity(String city);
    Restaurant getRestaurantByName(String name);
}
