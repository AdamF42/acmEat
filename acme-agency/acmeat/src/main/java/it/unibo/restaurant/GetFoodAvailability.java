package it.unibo.restaurant;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.entities.Restaurant;
import it.unibo.utils.UrlHelper;
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

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        com.sun.jersey.api.client.Client client = Client.create(clientConfig);
        RestaurantRepository repo = new RestaurantRepositoryImpl();

        try {

            Restaurant restaurant = repo.getRestaurantByName(requestOrder.restaurant);
            String queryUrl = UrlHelper.getUrlOrStringEmpty(restaurant)  + "availability";
            WebResource webResourcePut = client.resource(queryUrl);
            ClientResponse response = webResourcePut.accept("application/json")
                    .type("application/json").put(ClientResponse.class, requestOrder);

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
