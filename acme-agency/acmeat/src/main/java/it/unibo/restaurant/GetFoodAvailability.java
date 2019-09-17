package it.unibo.restaurant;

import com.sun.jersey.api.client.ClientResponse;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.entities.Restaurant;
import it.unibo.utils.UrlHelper;
import it.unibo.utils.WebResourceBuilder;
import it.unibo.utils.repo.RestaurantRepository;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static it.unibo.models.Status.AVAILABLE;
import static it.unibo.models.Status.NOT_AVAILABLE;
import static it.unibo.utils.AcmeVariables.RESTAURANT_AVAILABILITY;
import static it.unibo.utils.AcmeVariables.RESTAURANT_ORDER;

public class GetFoodAvailability implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        RestaurantOrder requestOrder = (RestaurantOrder) execution.getVariable(RESTAURANT_ORDER);
        RestaurantRepository repo = new RestaurantRepositoryImpl();

        try {

            Restaurant restaurant = repo.getRestaurantByName(requestOrder.restaurant);
            String queryUrl = UrlHelper.getUrlOrStringEmpty(restaurant)  + "availability";
            ClientResponse response = WebResourceBuilder.getBuilder(queryUrl).post(ClientResponse.class, requestOrder);

            if (response.getStatus() == OK.getStatusCode()) {
                RestaurantOrder responseOrder = response.getEntity(RestaurantOrder.class);
                if (responseOrder.status == AVAILABLE) {
                    execution.setVariable(RESTAURANT_ORDER, responseOrder);
                    execution.setVariable(RESTAURANT_AVAILABILITY, true);
                    return;
                }
            }
            requestOrder.status = NOT_AVAILABLE;
            execution.setVariable(RESTAURANT_ORDER, requestOrder);

        } catch (Exception e) {
            requestOrder.status = NOT_AVAILABLE;
            execution.setVariable(RESTAURANT_ORDER, requestOrder);
            e.printStackTrace();
        }
    }
}
