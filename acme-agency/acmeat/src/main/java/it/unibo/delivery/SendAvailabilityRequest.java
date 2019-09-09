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

            RestaurantOrder userOrder = (RestaurantOrder) delegateExecution.getVariable(RESTAURANT_ORDER);
            DeliveryOrder order = new DeliveryOrder();
            order.delivery_time = userOrder.delivery_time;
            order.src_address = userOrder.from;
            order.dest_address = userOrder.to;

            DeliveryCompany company = (DeliveryCompany) delegateExecution.getVariable(CURRENT_DELIVERY_COMPANY);

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
                    LOGGER.info("Company " + responseOrder.toString());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private DeliveryOrderList getAvailableDeliveryCompanies(List<CompletableFuture<ClientResponse>> webResources) {
        DeliveryOrderList deliveryCompanies = new DeliveryOrderList();
        for (CompletableFuture<ClientResponse> futureResponse : webResources) {
            try {
                ClientResponse response = futureResponse.get();
                if (response.getStatus() == OK.getStatusCode()) {
                    DeliveryOrder responseOrder = response.getEntity(DeliveryOrder.class);
                    if (responseOrder.status == Status.AVAILABLE) {
                        deliveryCompanies.addOrder(responseOrder);
                        LOGGER.info("Company " + responseOrder.toString());
                    }
                }
            } catch (Exception e) {
                LOGGER.warning(e.getMessage());
                e.printStackTrace();
            }
        }
        return deliveryCompanies;
    }

    private List<CompletableFuture<ClientResponse>> getCompletableFutures(DeliveryOrder order, List<DeliveryCompany> companies, ClientConfig clientConfig) {
        List<CompletableFuture<ClientResponse>> webResources = new ArrayList<>();
        for (DeliveryCompany company : companies) {

            Client client = Client.create(clientConfig);
            WebResource resource = client.resource(company.url + "availability");
            order.company = company.name;
            try {
                Supplier<ClientResponse> clientResponseSupplier = () -> resource.accept("application/json")
                        .type("application/json").put(ClientResponse.class, order);
                webResources.add(CompletableFuture.supplyAsync(clientResponseSupplier));
                LOGGER.info("Delivery order request: " + order.toString() + " uri: " + resource.getURI());
            } catch (Exception e) {
                LOGGER.warning(e.getMessage());
                e.printStackTrace();
            }
        }
        return webResources;
    }
}