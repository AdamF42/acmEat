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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantRepositoryImpl implements RestaurantRepository {

    private List<Restaurant> restaurants ;
    private Gson g = new Gson();

    @Override
    public List<Restaurant> getAvailableRestaurantsByCity(String city) throws FileNotFoundException {

        return this.restaurants
                .stream()
                .filter(company-> city.equals(company.city) && company.is_open)
                .collect(Collectors.toList());
    }

    @Override
    public Restaurant getRestaurantByName(String name) throws FileNotFoundException {

         return this.restaurants
                .stream()
                .filter(company-> name.equals(company.name))
                .findAny()
                .orElse(new Restaurant());
    }


    @Override
    public void setOpening(RestaurantAvailability availability) throws IOException {


        for (Restaurant restaurant : this.restaurants) {

            if (restaurant.name.equals(availability.restaurant)) {
                System.out.println("cambiando orari di apertura ");
                restaurant.setIs_open(availability.is_available);
                saveChanges();
                return;
            }
        }
    }

    public RestaurantRepositoryImpl() {
        try {
            JsonReader reader = new JsonReader(new FileReader("restaurant.json"));
            Restaurant[] restaurantsFile = g.fromJson(reader, Restaurant[].class);
            this.restaurants = Arrays.asList(restaurantsFile);
        } catch (Exception e){
            this.restaurants=new ArrayList<>();
        }
    }

    private void saveChanges() throws IOException {
        try (JsonWriter writer = new JsonWriter(new FileWriter("restaurant.json"))) {
            Gson gson = new GsonBuilder().create();
            JsonElement a=gson.toJsonTree(this.restaurants);
            gson.toJson(a, writer);
        }
    }

    @Override
    public void setMenu(RestaurantMenu restaurantMenuChange) throws IOException {

        for(int i =0;i<this.restaurants.size();i++) {
            if (this.restaurants.get(i).name.equals(restaurantMenuChange.restaurant)) {
                System.out.println("cambiando menu");
                this.restaurants.get(i).setMenu(restaurantMenuChange.menu);
                saveChanges();
                return;
            }
        }


    }
}
