package it.unibo.delivery;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.DeliveryOrder;
import it.unibo.models.RestaurantOrder;
import it.unibo.utils.Services;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class PlaceOrder implements JavaDelegate {
	
	@Override
    public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Place DeliveryOrder Delegate");

        // TODO: get order from db???
        RestaurantOrder restaurantOrder = (RestaurantOrder) execution.getVariable("userOrder");
        DeliveryOrder order = new DeliveryOrder();
		order.delivery_time = restaurantOrder.delivery_time;
		order.dest_address=restaurantOrder.to;
		order.src_address=restaurantOrder.from;

        try{
            //TODO: unmock
//            ClientConfig clientConfig = new DefaultClientConfig();
//            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//            com.sun.jersey.api.client.Client client = Client.create(clientConfig);
//
//            String getListURL = Services.DELIVERY_SERVICE_URL +"order";
//
//            WebResource webResourcePost = client.resource(getListURL);
//            ClientResponse response =  webResourcePost.accept("application/json")
//                    .type("application/json").post(ClientResponse.class, order);



            execution.setVariable("deliveryOrder", order);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
