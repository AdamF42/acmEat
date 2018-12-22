package it.unibo.delivery;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.DeliveryOrder;
import it.unibo.utils.Services;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class PlaceOrderDelegate implements JavaDelegate {
	
	@Override
    public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Place DeliveryOrder Delegate");
        try{
            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            com.sun.jersey.api.client.Client client = Client.create(clientConfig);

            String getListURL = Services.DELIVERY_SERVICE_URL +"order";

            DeliveryOrder order=new DeliveryOrder();
            order.deliveryTime="11";
            order.srcAddress="A";
            order.destAddress="B";

            WebResource webResourcePost = client.resource(getListURL);
            ClientResponse response =  webResourcePost.accept("application/json")
                    .type("application/json").post(ClientResponse.class, order);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
