package it.unibo.soseng;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;


public class restTest {

    public static void main( String[] args) {

//        try {
//
//            ClientConfig clientConfig = new DefaultClientConfig();
//            clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
//            Client client = Client.create(clientConfig);
//
//            String getListURL = "http://localhost:5000/delivery/get_availability/Bologna";
//
//            WebResource webResourceGet = client.resource(getListURL);
//
//            ClientResponse response = webResourceGet.get(ClientResponse.class);
//
//            Company[] companies = response.getEntity(Company[].class);
//
//
//            for (Company company: companies) {
//                System.out.println(company.toString());
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
