package it.unibo.app;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.app.models.RestaurantOrder;
import org.json.simple.JSONObject;

import static it.unibo.app.fakes.fakeRestaurantOrder.orderOne;


public class restTest {
    private final static String BASE_URL = "http://localhost:5000/restaurant/order";

    public static void main( String[] args) {

        try {

            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            Client client = Client.create(clientConfig);

            WebResource webResource = client.resource(BASE_URL);

            JSONObject input = orderOne();

            ClientResponse response = webResource.type("application/json")
                    .post(ClientResponse.class, input);

            RestaurantOrder order = response.getEntity(RestaurantOrder.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println(order.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
