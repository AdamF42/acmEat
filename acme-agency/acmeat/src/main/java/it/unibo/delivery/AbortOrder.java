package it.unibo.delivery;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.DeliveryOrder;
import it.unibo.models.Status;
import it.unibo.models.entities.DeliveryCompany;
import it.unibo.utils.UrlHelper;
import it.unibo.utils.repo.impl.DeliveryCompaniesRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static it.unibo.utils.AcmeVariables.DELIVERY_ORDER;


public class AbortOrder implements JavaDelegate {

    private DeliveryCompaniesRepositoryImpl repo = new DeliveryCompaniesRepositoryImpl();
    private ClientConfig clientConfig = new DefaultClientConfig();
    private final Logger LOGGER = Logger.getLogger(AbortOrder.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        try {
            // retrieve delivery order
            DeliveryOrder deliveryOrder = (DeliveryOrder) execution.getVariable(DELIVERY_ORDER);

            DeliveryCompany company = repo.getCompanyByName(deliveryOrder.company);
            String queryURL = UrlHelper.getUrlOrStringEmpty(company) + "order/" + deliveryOrder.id
                    + "/status/" + Status.ABORTED.toString();

            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            Client client = Client.create(clientConfig);
            WebResource webResourcePost = client.resource(queryURL);
            ClientResponse response = webResourcePost
                    .accept("application/json")
                    .type("application/json")
                    .put(ClientResponse.class);

            if (response.getStatus() == OK.getStatusCode()) {
                execution.setVariable(DELIVERY_ORDER, response.getEntity(DeliveryOrder.class));
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe(e.getMessage());
        }
    }


}
