package it.unibo.delivery;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.utils.Services;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class UpdateOrderDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		
		System.out.println("Update order delegate");
		try{
			ClientConfig clientConfig = new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			com.sun.jersey.api.client.Client client = Client.create(clientConfig);
			String id="1";
			String getListURL = Services.DELIVERY_SERVICE_URL +"order"+"/"+id;

			WebResource webResourcePut = client.resource(getListURL);
			ClientResponse response =  webResourcePut.accept("application/json")
					.type("application/json").put(ClientResponse.class);



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}