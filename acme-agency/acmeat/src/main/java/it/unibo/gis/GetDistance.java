package it.unibo.gis;

import com.sun.istack.NotNull;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.Distance;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.entities.DeliveryCompany;
import it.unibo.utils.Services;
import it.unibo.utils.repo.DeliveryCompaniesRepository;
import it.unibo.utils.repo.impl.DeliveryCompaniesRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static it.unibo.utils.AcmeVariables.*;

public class GetDistance implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(GetDistance.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        try {
            DeliveryCompany company = (DeliveryCompany) delegateExecution.getVariable(CURRENT_DELIVERY_COMPANY);

            String toDistance = (String) delegateExecution.getVariable("city");
            String fromDistance = company.address;

            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            com.sun.jersey.api.client.Client client = Client.create(clientConfig);
            String queryURL = Services.GIS_SERVICE_URL + getURLforGis(fromDistance, toDistance);
            WebResource webResourceGet = client.resource(queryURL);

            ClientResponse response = webResourceGet
                    .accept("application/json")
                    .type("application/json")
                    .get(ClientResponse.class);

            if (response.getStatus() == OK.getStatusCode()) {
                Distance distance = response.getEntity(Distance.class);
                delegateExecution.setVariable(DISTANCE, distance.distance);
                LOGGER.info("GetDistance: " + distance.distance + "\nfrom: " + fromDistance + "\nto: " + toDistance);
            } else {
                delegateExecution.setVariable(DISTANCE, Double.MAX_VALUE);
                LOGGER.info("Not able to get distance");
            }

        } catch (Exception ex) {
            delegateExecution.setVariable(DISTANCE, Double.MAX_VALUE);
            LOGGER.severe(ex.getMessage());
        }
    }

    @NotNull
    private String getURLforGis(String from, String to) throws UnsupportedEncodingException {
        return "getDistance?from=" +
                URLEncoder.encode(from, StandardCharsets.UTF_8.name()) +
                "&to=" +
                URLEncoder.encode(to, StandardCharsets.UTF_8.name());
    }

}
