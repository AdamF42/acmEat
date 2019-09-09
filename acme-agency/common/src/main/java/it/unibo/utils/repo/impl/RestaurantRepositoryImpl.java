package it.unibo.utils.repo.impl;

import it.unibo.models.RestaurantAvailability;
import it.unibo.models.RestaurantMenu;
import it.unibo.models.entities.Restaurant;
import it.unibo.utils.repo.DataBase;
import it.unibo.utils.repo.RestaurantRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantRepositoryImpl implements RestaurantRepository {

    private DataBase db;

    public RestaurantRepositoryImpl() {
        this.db = new DataBase();
    }

    public RestaurantRepositoryImpl(DataBase db) {
        this.db = db;
    }

    @Override
    public List<Restaurant> getAvailableRestaurantsByCity(String city) {

        return this.db.restaurants
                .stream()
                .filter(company -> company.city.contains(city) && company.is_open)
                .collect(Collectors.toList());
    }

    @Override
    public Restaurant getRestaurantByName(String name) {

        return this.db.restaurants
                .stream()
                .filter(company -> name.equals(company.name))
                .findAny()
                .orElse(new Restaurant());
    }

    @Override
    public void addOrUpdateOpeningTime(RestaurantAvailability availability) throws IOException {

        this.db.restaurants
                .stream()
                .filter(restaurant -> availability.name.equals(restaurant.name))
                .forEach(restaurant -> restaurant.is_open = availability.is_available);

        db.saveChanges();
    }

    @Override
    public void addOrUpdateMenu(RestaurantMenu restaurantMenuChange) throws IOException {

        this.db.restaurants
                .stream()
                .filter(restaurant -> restaurantMenuChange.name.equals(restaurant.name))
                .forEach(restaurant -> restaurant.updateMenu(restaurantMenuChange.menu));

        db.saveChanges();
    }

}
