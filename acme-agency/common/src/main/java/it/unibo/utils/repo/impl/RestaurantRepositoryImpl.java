package it.unibo.utils.repo.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.unibo.models.RestaurantAvailability;
import it.unibo.models.RestaurantMenu;
import it.unibo.models.entities.Restaurant;
import it.unibo.utils.repo.RestaurantRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantRepositoryImpl implements RestaurantRepository {

    private List<Restaurant> restaurants;
    private static final String DATABASE = "restaurant.json";

    @Override
    public List<Restaurant> getAvailableRestaurantsByCity(String city) {

        return this.restaurants
                .stream()
                .filter(company -> city.equals(company.city) && company.is_open)
                .collect(Collectors.toList());
    }

    @Override
    public Restaurant getRestaurantByName(String name) {

        return this.restaurants
                .stream()
                .filter(company -> name.equals(company.name))
                .findAny()
                .orElse(new Restaurant());
    }

    @Override
    public void addOrUpdateOpeningTime(RestaurantAvailability availability) throws IOException {

        this.restaurants
                .stream()
                .filter(restaurant -> availability.name.equals(restaurant.name))
                .forEach(restaurant -> restaurant.is_open = availability.is_available);

        saveChanges();
    }

    @Override
    public void addOrUpdateMenu(RestaurantMenu restaurantMenuChange) throws IOException {

        this.restaurants
                .stream()
                .filter(restaurant -> restaurantMenuChange.name.equals(restaurant.name))
                .forEach(restaurant -> restaurant.setMenu(restaurantMenuChange.menu));

        saveChanges();
    }

    public RestaurantRepositoryImpl() {
        try {
            JsonReader reader = new JsonReader(new FileReader(DATABASE));
            Gson g = new Gson();
            //TODO: use RestaurantList
            Restaurant[] restaurantsFile = g.fromJson(reader, Restaurant[].class);
            this.restaurants = Arrays.asList(restaurantsFile);
        } catch (Exception e) {
            this.restaurants = new ArrayList<>();
        }
    }

    //TODO: move in DataBase
    private void saveChanges() throws IOException {
        try (JsonWriter writer = new JsonWriter(new FileWriter(DATABASE))) {
            Gson gson = new GsonBuilder().create();
            JsonElement a = gson.toJsonTree(this.restaurants);
            gson.toJson(a, writer);
        }
    }
}
