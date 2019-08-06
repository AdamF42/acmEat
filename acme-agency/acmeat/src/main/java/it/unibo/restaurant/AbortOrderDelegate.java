package it.unibo.restaurant;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.Status;
import it.unibo.utils.Services;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AbortOrderDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        try{
            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            com.sun.jersey.api.client.Client client = Client.create(clientConfig);
            String id="1";
            String getListURL = Services.restaurantServiceUrl +"order/abort";

            RestaurantOrder order=new RestaurantOrder();
            order.content="lasagne";
            order.deliveryTime="11";
            order.id=1;
            order.status= Status.accepted;
            WebResource webResourcePut = client.resource(getListURL);
            ClientResponse response =  webResourcePut.accept("application/json")
                    .type("application/json").put(ClientResponse.class,order);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
