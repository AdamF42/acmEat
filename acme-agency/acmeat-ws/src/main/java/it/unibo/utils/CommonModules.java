package it.unibo.utils;

import camundajar.com.google.gson.Gson;
import camundajar.com.google.gson.GsonBuilder;
import it.unibo.utils.repo.RestaurantRepository;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;

import javax.ws.rs.Produces;

public class CommonModules {

    @Produces
    public Gson getGson() {
        return new GsonBuilder().serializeNulls().create();
    }

    @Produces
    public RestaurantRepository getRestaurantRepository() {
        return new RestaurantRepositoryImpl();
    }

}
