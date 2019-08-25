package it.unibo.utils.repo.impl;

import it.unibo.models.entities.Restaurant;
import it.unibo.utils.repo.DataBase;
import it.unibo.utils.repo.RestaurantRepository;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantRepositoryImpl implements RestaurantRepository {

    @Override
    public List<Restaurant> getRestaurantsByCity(String city) {

        return DataBase.restaurants
                .stream()
                .filter(company-> city.equals(company.city))
                .collect(Collectors.toList());
    }

    @Override
    public Restaurant getRestaurantByName(String name) {

         return  DataBase.restaurants
                .stream()
                .filter(company-> name.equals(company.name))
                .findAny()
                .orElse(new Restaurant());
    }
}
