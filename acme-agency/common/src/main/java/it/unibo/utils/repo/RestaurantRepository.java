package it.unibo.utils.repo;

import it.unibo.models.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> getRestaurantByCity(String city);
}
