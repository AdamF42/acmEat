package it.unibo.restaurant;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.RestaurantOrder;
import it.unibo.utils.Services;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendOrder implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("Send order delegate");

        try{


            RestaurantOrder order = (RestaurantOrder) execution.getVariable("userOrder");

            //TODO: unmock
//            ClientConfig clientConfig = new DefaultClientConfig();
//            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//            com.sun.jersey.api.client.Client client = Client.create(clientConfig);
//            String getListURL = Services.RESTAURANT_SERVICE_URL +"order";
//            WebResource webResourcePost = client.resource(getListURL);
//            RestaurantOrder responseOrder =  webResourcePost.accept("application/json")
//                    .type("application/json").post(RestaurantOrder.class, order);

            //order.status = Status.available;

            //TODO: all this work should be done in ws...

//            execution.setVariable("userOrder",responseOrder); //TODO: order should be stored in db
//            execution.setVariable("foodAvailability",true);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
