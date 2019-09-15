package it.unibo.delivery;

import com.sun.jersey.api.client.ClientResponse;
import it.unibo.models.DeliveryOrder;
import it.unibo.models.Status;
import it.unibo.models.entities.DeliveryCompany;
import it.unibo.utils.UrlHelper;
import it.unibo.utils.WebResourceBuilder;
import it.unibo.utils.repo.impl.DeliveryCompaniesRepositoryImpl;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static it.unibo.utils.AcmeErrorMessages.UNAVAILABLE_DELIVERY_COMPANY;
import static it.unibo.utils.AcmeVariables.DELIVERY_ORDER;


public class AbortOrder implements JavaDelegate {

    private DeliveryCompaniesRepositoryImpl repo = new DeliveryCompaniesRepositoryImpl();
    private final Logger LOGGER = Logger.getLogger(AbortOrder.class.getName());

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        try {
            DeliveryOrder deliveryOrder = (DeliveryOrder) execution.getVariable(DELIVERY_ORDER);

            DeliveryCompany company = repo.getCompanyByName(deliveryOrder.company);
            String queryURL = UrlHelper.getUrlOrStringEmpty(company) + "order/" + deliveryOrder.id
                    + "/status/" + Status.ABORTED.toString();

            ClientResponse response = WebResourceBuilder.getBuilder(queryURL).put(ClientResponse.class);

            if (response.getStatus() == OK.getStatusCode()) {
                execution.setVariable(DELIVERY_ORDER, response.getEntity(DeliveryOrder.class));
            }

        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new BpmnError(UNAVAILABLE_DELIVERY_COMPANY);
        }
    }


}
