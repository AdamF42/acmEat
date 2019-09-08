package it.unibo.acme;

import camundajar.com.google.gson.Gson;
import it.unibo.models.RestaurantList;
import it.unibo.utils.repo.RestaurantRepository;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static it.unibo.utils.AcmeVariables.RESTAURANTS;


public class GetRestaurantByCity implements JavaDelegate {


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String city = (String) delegateExecution.getVariable("city");
        RestaurantRepository repo = new RestaurantRepositoryImpl();
        RestaurantList restaurantList = new RestaurantList();
        Gson g = new Gson();
        restaurantList.setRestaurants(repo.getAvailableRestaurantsByCity(city));

        if (!restaurantList.isEmpty())
            delegateExecution.setVariable(RESTAURANTS, g.toJson(restaurantList));

    }
}
