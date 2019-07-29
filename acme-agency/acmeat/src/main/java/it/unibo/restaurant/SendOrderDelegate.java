package it.unibo.restaurant;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.RestaurantOrder;
import it.unibo.utils.Services;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendOrderDelegate  implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("Send order delegate");

        try{
            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            com.sun.jersey.api.client.Client client = Client.create(clientConfig);

            String getListURL = Services.restaurantServiceUrl +"order";

            RestaurantOrder order=new RestaurantOrder();
            order.content="lasagne";
            order.deliveryTime="11";

            WebResource webResourcePost = client.resource(getListURL);
            ClientResponse response =  webResourcePost.accept("application/json")
                    .type("application/json").post(ClientResponse.class, order);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
