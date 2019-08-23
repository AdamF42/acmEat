package it.unibo.acme;

import camundajar.com.google.gson.Gson;
import camundajar.com.google.gson.GsonBuilder;
import it.unibo.models.RestaurantList;
import it.unibo.utils.repo.RestaurantRepository;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class GetRestaurantByCity implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        Gson g = builder.create();

        String city = (String) delegateExecution.getVariable("city");
        RestaurantRepository repo = new RestaurantRepositoryImpl();
        RestaurantList restaurants = new RestaurantList();
        restaurants.setRestaurants(repo.getRestaurantsByCity(city));

        delegateExecution.setVariable("restaurants", g.toJson(restaurants));

    }
}
