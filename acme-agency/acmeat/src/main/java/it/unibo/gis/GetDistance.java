package it.unibo.gis;

import com.sun.istack.NotNull;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.Distance;
import it.unibo.models.RestaurantOrder;
import it.unibo.utils.Services;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.*;

public class GetDistance implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(GetDistance.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        RestaurantOrder order = (RestaurantOrder) delegateExecution.getVariable(RESTAURANT_ORDER);
        String fromDistance = order.from;
        String toDistance = order.to;
        try {
            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            com.sun.jersey.api.client.Client client = Client.create(clientConfig);
            String queryURL = Services.GIS_SERVICE_URL + getURLforGis(fromDistance,toDistance);
            WebResource webResourceGet = client.resource(queryURL);
            Distance distance =  webResourceGet.accept("application/json")
                    .type("application/json").get(Distance.class);

            delegateExecution.setVariable(DISTANCE, distance.distance);
            LOGGER.info("GetDistance: " + distance.distance + "\nfrom: " + fromDistance + "\nto: " + toDistance);

        } catch (Exception ex){
            LOGGER.warning(ex.getMessage());
        }
    }

    @NotNull
    private String getURLforGis(String from, String to) throws UnsupportedEncodingException {
        return "getDistance?from="+
                URLEncoder.encode(from,StandardCharsets.UTF_8.name())+
                "&to="+
                URLEncoder.encode(to,StandardCharsets.UTF_8.name());
    }

}
