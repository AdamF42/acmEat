package it.unibo.app.services;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.app.models.RestaurantOrder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class RestaurantDelegate implements JavaDelegate {

  private final static String BASE_URL = "http://localhost:5000/restaurant/order";

  public void execute(DelegateExecution execution) throws Exception {

//    String order = execution.getVariable().toString(); TODO: use it to get variable from camunda execution

    try {

      ClientConfig clientConfig = new DefaultClientConfig();
      clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
      Client client = Client.create(clientConfig);

      WebResource webResource = client.resource(BASE_URL);

      String input = "{\"content\": [ \"pizza\", \"carbonara\" ],\"delivery_time\": \"13\"}";

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

