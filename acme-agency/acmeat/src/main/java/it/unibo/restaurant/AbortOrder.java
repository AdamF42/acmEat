package it.unibo.restaurant;

import com.sun.jersey.api.client.ClientResponse;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.entities.Restaurant;
import it.unibo.utils.UrlHelper;
import it.unibo.utils.WebResourceBuilder;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static it.unibo.utils.AcmeErrorMessages.UNAVAILABLE_RESTAURANT;
import static it.unibo.utils.AcmeVariables.RESTAURANT_ORDER;

public class AbortOrder implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(AbortOrder.class.getName());
    private RestaurantRepositoryImpl repo = new RestaurantRepositoryImpl();

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        try {

            RestaurantOrder order = (RestaurantOrder) execution.getVariable(RESTAURANT_ORDER);

            Restaurant restaurant = repo.getRestaurantByName(order.restaurant);
            String queryURL = UrlHelper.getUrlOrStringEmpty(restaurant) + "order/abort";

            ClientResponse response = WebResourceBuilder.getBuilder(queryURL).put(ClientResponse.class, order);

            if (response.getStatus() == OK.getStatusCode()) {
                execution.setVariable(RESTAURANT_ORDER, response.getEntity(RestaurantOrder.class));
            }

        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new BpmnError(UNAVAILABLE_RESTAURANT);
        }
    }
}
