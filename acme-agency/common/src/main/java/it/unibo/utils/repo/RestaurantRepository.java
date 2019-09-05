package it.unibo.utils.repo;

import it.unibo.models.RestaurantAvailability;
import it.unibo.models.RestaurantMenu;
import it.unibo.models.entities.Restaurant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface RestaurantRepository {
    List<Restaurant> getAvailableRestaurantsByCity(String city) throws FileNotFoundException;
    Restaurant getRestaurantByName(String name) throws FileNotFoundException;
    void addOrUpdateOpeningTime(RestaurantAvailability availability) throws IOException;
    void addOrUpdateMenu(RestaurantMenu restaurantMenuChange ) throws IOException;
}
