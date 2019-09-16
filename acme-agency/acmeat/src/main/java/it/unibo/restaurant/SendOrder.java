package it.unibo.restaurant;

import com.sun.jersey.api.client.ClientResponse;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.entities.Restaurant;
import it.unibo.utils.UrlHelper;
import it.unibo.utils.WebResourceBuilder;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static it.unibo.utils.AcmeVariables.RESTAURANT_ORDER;

public class SendOrder implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(SendOrder.class.getName());
    private RestaurantRepositoryImpl repo = new RestaurantRepositoryImpl();

    @Override
    public void execute(DelegateExecution execution) {

        try {

            RestaurantOrder order = (RestaurantOrder) execution.getVariable(RESTAURANT_ORDER);
            Restaurant restaurant = repo.getRestaurantByName(order.restaurant);
            String queryUrl = UrlHelper.getUrlOrStringEmpty(restaurant) + "order";

            ClientResponse response = WebResourceBuilder.getBuilder(queryUrl).post(ClientResponse.class, order);

            if (response.getStatus() == OK.getStatusCode()) {
                RestaurantOrder restaurantOrder = response.getEntity(RestaurantOrder.class);
                execution.setVariable(RESTAURANT_ORDER, restaurantOrder);
                LOGGER.info("RestaurantOrder Status: " + order.getStatus().name());
            } else {
                LOGGER.info("Restaurant returned error code: " + response.getStatus());
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }
}
