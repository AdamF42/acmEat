package it.unibo.acme;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.LoggerDelegate;
import it.unibo.models.DeliveryOrder;
import it.unibo.utils.Services;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.*;
import static it.unibo.utils.Services.getRandomNumberInRange;

public class SendAvailabilityRequest implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

//        ClientConfig clientConfig = new DefaultClientConfig();
//        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//        com.sun.jersey.api.client.Client client = Client.create(clientConfig);
//
//        String getListURL = Services.DELIVERY_SERVICE_URL +"order";
//
//        DeliveryOrder order = new DeliveryOrder();
//
//        // TODO: check fot time limit
//        order.deliveryTime = String.valueOf(getRandomNumberInRange(1, 24));
//        order.srcAddress = delegateExecution.getVariable(FROM).toString();
//        order.destAddress= delegateExecution.getVariable(TO).toString();
//
//        WebResource webResourcePost = client.resource(getListURL);
//
//        Supplier<ClientResponse> clientResponseSupplier = () -> webResourcePost.accept("application/json")
//                .type("application/json").post(ClientResponse.class, order);
//
//        CompletableFuture<ClientResponse> firstDeliveryCompany = CompletableFuture.supplyAsync(clientResponseSupplier);
//        CompletableFuture<ClientResponse> secondDeliveryCompany = CompletableFuture.supplyAsync(clientResponseSupplier);

//        Map<String, Double> deliveryCompanies = new HashMap<>();
//        deliveryCompanies.put(DELIVERY_COMPANY_ONE, firstDeliveryCompany.get().getEntity(DeliveryOrder.class).price);
//        deliveryCompanies.put(DELIVERY_COMPANY_TWO, secondDeliveryCompany.get().getEntity(DeliveryOrder.class).price);
        Map<String, Double> deliveryCompanies = new HashMap<>();
        deliveryCompanies.put(DELIVERY_COMPANY_ONE, 12.0);
        deliveryCompanies.put(DELIVERY_COMPANY_TWO, 13.0);

        LOGGER.info("Company1: " + deliveryCompanies.get(DELIVERY_COMPANY_ONE) +
                "\nCompany2: " + deliveryCompanies.get(DELIVERY_COMPANY_TWO));

        delegateExecution.setVariable(DELIVERY_COMPANIES_PROPOSAL, deliveryCompanies);

    }
}
