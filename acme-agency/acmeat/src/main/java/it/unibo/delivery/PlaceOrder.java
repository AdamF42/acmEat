package it.unibo.delivery;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.DeliveryOrder;
import it.unibo.utils.repo.impl.DeliveryCompaniesRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import java.util.logging.Logger;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static it.unibo.utils.AcmeVariables.DELIVERY_ORDER;


public class PlaceOrder implements JavaDelegate {

    private  Gson g = new Gson();
	private DeliveryCompaniesRepositoryImpl repo = new DeliveryCompaniesRepositoryImpl();
    private ClientConfig clientConfig = new DefaultClientConfig();
    private final Logger LOGGER = Logger.getLogger(PlaceOrder.class.getName());


    @Override
    public void execute(DelegateExecution execution) throws Exception {

        try{

            // retrieve delivery order
            DeliveryOrder deliveryOrder = g
                    .fromJson ((String) execution.getVariable(DELIVERY_ORDER),DeliveryOrder.class);

            // TODO: not handled possible null reference exception???
            String queryURL = repo.getCompanyByName(deliveryOrder.company).url +"order";

            //TODO: add logs
            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            com.sun.jersey.api.client.Client client = Client.create(clientConfig);
            WebResource webResourcePost = client.resource(queryURL);
            ClientResponse response =  webResourcePost
                    .accept("application/json")
                    .type("application/json")
                    .post(ClientResponse.class, deliveryOrder);

            if (response.getStatus() == OK.getStatusCode()) {
                execution.setVariable(DELIVERY_ORDER, response.getEntity(DeliveryOrder.class));
            }

        } catch (Exception e) {
            //TODO: log properly
            e.printStackTrace();
            LOGGER.severe(e.getMessage());        }
    }


}
