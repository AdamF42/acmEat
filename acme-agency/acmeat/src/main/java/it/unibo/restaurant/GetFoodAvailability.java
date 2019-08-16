package it.unibo.restaurant;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.Status;
import it.unibo.utils.repo.RestaurantRepository;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static it.unibo.utils.AcmeVariables.RESTAURANT_AVAILABLE;
import static it.unibo.utils.AcmeVariables.RESTAURANT_ORDER;

public class GetFoodAvailability implements JavaDelegate {

	    @Override
	    public void execute(DelegateExecution execution) throws Exception {

			try{

				Gson g = new Gson();
				// TODO: find out why you can't deserialize the object...
				String serializedOrder = (String) execution.getVariable(RESTAURANT_ORDER);

				RestaurantOrder order = g.fromJson(serializedOrder,RestaurantOrder.class);

				ClientConfig clientConfig = new DefaultClientConfig();
				clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
				com.sun.jersey.api.client.Client client = Client.create(clientConfig);

				RestaurantRepository repo = new RestaurantRepositoryImpl();
				String baseUrl =  repo.getRestaurantByName(order.restaurant).url;
				String queryUrl = baseUrl +"availability";

				WebResource webResourcePut = client.resource(queryUrl);

				RestaurantOrder responseOrder =  webResourcePut.accept("application/json")
						.type("application/json").put(RestaurantOrder.class, order);

				serializedOrder = g.toJson(responseOrder);

				execution.setVariable(RESTAURANT_ORDER,  serializedOrder);

				//TODO: it is better to use userOrder only...
				if(responseOrder.status== Status.AVAILABLE)
					execution.setVariable(RESTAURANT_AVAILABLE,  "");

			} catch (Exception e) {
				// TODO: set variable to signal error???
				// TODO: add logs
				e.printStackTrace();
			}


	    }
}
