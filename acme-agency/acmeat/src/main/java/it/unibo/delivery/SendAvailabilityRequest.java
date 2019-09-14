package it.unibo.delivery;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.DeliveryOrder;
import it.unibo.models.DeliveryOrderList;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.Status;
import it.unibo.models.entities.DeliveryCompany;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static it.unibo.utils.AcmeVariables.*;

public class SendAvailabilityRequest implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(SendAvailabilityRequest.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        try {
            DeliveryCompany company = (DeliveryCompany) delegateExecution.getVariable(CURRENT_DELIVERY_COMPANY);
            RestaurantOrder userOrder = (RestaurantOrder) delegateExecution.getVariable(RESTAURANT_ORDER);
            DeliveryOrder order = new DeliveryOrder();
            order.delivery_time = userOrder.delivery_time;
            order.src_address = userOrder.from;
            order.dest_address = userOrder.to;
            order.company = company.name;

            LOGGER.info("DeliveryCompany: "+ company.name);
            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            Client client = Client.create(clientConfig);
            WebResource webResourceGet = client.resource(company.url + "availability");
            ClientResponse response = webResourceGet
                    .accept("application/json")
                    .type("application/json")
                    .put(ClientResponse.class, order);

            if (response.getStatus() == OK.getStatusCode()) {
                DeliveryOrder responseOrder = response.getEntity(DeliveryOrder.class);
                if (responseOrder.status == Status.AVAILABLE) {
                    delegateExecution.setVariable(CURRENT_DELIVERY_ORDER, responseOrder);
                    LOGGER.info("DeliveryCompany Response| " + responseOrder.status);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
