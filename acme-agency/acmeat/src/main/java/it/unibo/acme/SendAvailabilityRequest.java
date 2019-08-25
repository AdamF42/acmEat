package it.unibo.acme;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.LoggerDelegate;
import it.unibo.models.entities.DeliveryCompany;
import it.unibo.models.DeliveryOrder;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.Status;
import it.unibo.utils.repo.DeliveryCompaniesRepository;
import it.unibo.utils.repo.impl.DeliveryCompaniesRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static it.unibo.utils.AcmeVariables.*;

public class SendAvailabilityRequest implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Gson g = new Gson();
        // TODO: find out why you can't deserialize the object...
        String serializedOrder = (String) delegateExecution.getVariable(RESTAURANT_ORDER);
        RestaurantOrder userOrder = g.fromJson(serializedOrder,RestaurantOrder.class);

        DeliveryOrder order = new DeliveryOrder();
        order.delivery_time = userOrder.delivery_time;
        order.src_address = userOrder.from;
        order.dest_address = userOrder.to;

        DeliveryCompaniesRepository repo = new DeliveryCompaniesRepositoryImpl();

        List<DeliveryCompany> companies = repo.getAllDeliveryCompanies();

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        List<CompletableFuture<ClientResponse>> webResources = new ArrayList<>();
        for (DeliveryCompany company :companies) {

            com.sun.jersey.api.client.Client client = Client.create(clientConfig);
            WebResource resource = client.resource(company.url +"availability");
            order.company = company.name;
            Supplier<ClientResponse> clientResponseSupplier = () -> resource.accept( "application/json")
                    .type("application/json").put(ClientResponse.class, order);
            webResources.add(CompletableFuture.supplyAsync(clientResponseSupplier));
            LOGGER.info("Delivery order request: "+g.toJson(order) +" uri: "+resource.getURI());
        }

        HashMap<String, String> deliveryCompanies = new HashMap<>();
        for (CompletableFuture<ClientResponse> futureResponse: webResources) {
            ClientResponse response = futureResponse.get();

            if (response.getStatus() == OK.getStatusCode()){
                DeliveryOrder responseOrder = response.getEntity(DeliveryOrder.class);
                if(responseOrder.status == Status.AVAILABLE){
                    deliveryCompanies.put(responseOrder.company,g.toJson(responseOrder));
                    LOGGER.info("Company "+ g.toJson(responseOrder));
                }
            }
        }

        delegateExecution.setVariable(DELIVERY_COMPANIES_PROPOSAL, deliveryCompanies);
    }
}
