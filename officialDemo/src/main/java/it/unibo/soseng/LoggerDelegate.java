package it.unibo.soseng;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
/**
 * This is an easy adapter implementation 
 * illustrating how a Java Delegate can be used 
 * from within a BPMN 2.0 Service Task.
 */
public class LoggerDelegate implements JavaDelegate {
 

  public void execute(DelegateExecution execution) throws Exception {
    
    System.out.println("\n\n  ... LoggerDelegate invoked by "
            + "processDefinitionId=" + execution.getProcessDefinitionId()
            + ", activtyId=" + execution.getCurrentActivityId()
            + ", activtyName='" + execution.getCurrentActivityName() + "'"
            + ", processInstanceId=" + execution.getProcessInstanceId()
            + ", businessKey=" + execution.getProcessBusinessKey()
            + ", executionId=" + execution.getId()
            + " \n\n");
    try {

      ClientConfig clientConfig = new DefaultClientConfig();
      clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
      com.sun.jersey.api.client.Client client = Client.create(clientConfig);

      String getListURL = "http://localhost:5000/delivery/get_availability/Bologna";

      WebResource webResourceGet = client.resource(getListURL);

      ClientResponse response = webResourceGet.get(ClientResponse.class);

      Company[] companies = response.getEntity(Company[].class);


      for (Company company: companies) {
        System.out.println(company.toString());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
