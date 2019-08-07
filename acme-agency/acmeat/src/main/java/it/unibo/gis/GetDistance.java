package it.unibo.gis;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.LoggerDelegate;
import it.unibo.utils.Services;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.*;

public class GetDistance implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try{
            String fromDistance = delegateExecution.getVariable(FROM).toString();
            String toDistance = delegateExecution.getVariable(TO).toString();

            //TODO unmock
//            ClientConfig clientConfig = new DefaultClientConfig();
//            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//            com.sun.jersey.api.client.Client client = Client.create(clientConfig);
//            String getListURL = Services.GIS_SERVICE_URL + Services.getURLforGis(fromDistance,toDistance);
//            WebResource webResourceGet = client.resource(getListURL);
//            ClientResponse response =  webResourceGet.accept("application/json")
//                    .type("application/json").get(ClientResponse.class);
//
//            double distance = response.getEntity(Integer.class);

            double distance = 12345;

            delegateExecution.setVariable(DISTANCE, distance);

            LOGGER.info("GetDistance: " + distance + "\nfrom: " + fromDistance + "\nto: " + toDistance);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
