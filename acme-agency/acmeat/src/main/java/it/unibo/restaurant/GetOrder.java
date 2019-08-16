package it.unibo.restaurant;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GetOrder implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("Get order delegate");


//        try{
//            ClientConfig clientConfig = new DefaultClientConfig();
//            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//            com.sun.jersey.api.client.Client client = Client.create(clientConfig);
//
//
//            RestaurantRepository repo = new RestaurantRepositoryImpl();
//            String baseUrl =  repo.getRestaurantByName(order.restaurant).url;
//            String queryUrl = baseUrl +"availability";
//
//            String getListURL = Services.RESTAURANT_SERVICE_URL +"order"+"/"+id;
//
//            WebResource webResourceGet = client.resource(getListURL);
//            ClientResponse response =  webResourceGet.accept("application/json")
//                    .type("application/json").get(ClientResponse.class);
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
