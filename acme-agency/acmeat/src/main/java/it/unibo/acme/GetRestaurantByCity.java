package it.unibo.acme;

import com.google.gson.Gson;
import it.unibo.models.Restaurant;
import it.unibo.utils.repo.RestaurantRepository;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.List;

public class GetRestaurantByCity implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String city = (String) delegateExecution.getVariable("city");
        RestaurantRepository repo = new RestaurantRepositoryImpl();
        List<Restaurant> restaurants = repo.getRestaurantsByCity(city);
        Gson g = new Gson();
        delegateExecution.setVariable("restaurants", g.toJson(restaurants));

    }
}
