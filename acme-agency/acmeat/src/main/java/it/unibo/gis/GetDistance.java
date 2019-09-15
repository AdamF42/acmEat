package it.unibo.gis;

import com.sun.istack.NotNull;
import com.sun.jersey.api.client.ClientResponse;
import it.unibo.models.Distance;
import it.unibo.models.entities.DeliveryCompany;
import it.unibo.utils.Services;
import it.unibo.utils.WebResourceBuilder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static it.unibo.utils.AcmeVariables.CURRENT_DELIVERY_COMPANY;
import static it.unibo.utils.AcmeVariables.DISTANCE;

public class GetDistance implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(GetDistance.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) {

        Integer distance = Integer.MAX_VALUE;
        try {
            DeliveryCompany company = (DeliveryCompany) delegateExecution.getVariable(CURRENT_DELIVERY_COMPANY);

            String toDistance = (String) delegateExecution.getVariable("city");
            String fromDistance = company.address;

            String queryURL = Services.GIS_SERVICE_URL + getURLforGis(fromDistance, toDistance);

            ClientResponse response = WebResourceBuilder.getBuilder(queryURL).get(ClientResponse.class);

            if (response.getStatus() == OK.getStatusCode()) {
                Distance responseDistance = response.getEntity(Distance.class);
                distance = responseDistance.distance;
                LOGGER.info("GetDistance: " + responseDistance.distance + "\nfrom: " + fromDistance + "\nto: " + toDistance);
            } else {
                LOGGER.info("Not able to get distance");
            }

        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
        }
        delegateExecution.setVariable(DISTANCE, distance);
    }

    @NotNull
    private String getURLforGis(String from, String to) throws UnsupportedEncodingException {
        return "getDistance?from=" +
                URLEncoder.encode(from, StandardCharsets.UTF_8.name()) +
                "&to=" +
                URLEncoder.encode(to, StandardCharsets.UTF_8.name());
    }


}
