package it.unibo.delivery;

import com.sun.jersey.api.client.ClientResponse;
import it.unibo.models.DeliveryOrder;
import it.unibo.models.entities.DeliveryCompany;
import it.unibo.utils.UrlHelper;
import it.unibo.utils.WebResourceBuilder;
import it.unibo.utils.repo.impl.DeliveryCompaniesRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;
import static it.unibo.utils.AcmeVariables.DELIVERY_ORDER;


public class PlaceOrder implements JavaDelegate {

    private DeliveryCompaniesRepositoryImpl repo = new DeliveryCompaniesRepositoryImpl();
    private final Logger LOGGER = Logger.getLogger(PlaceOrder.class.getName());

    @Override
    public void execute(DelegateExecution execution) {

        try {

            DeliveryOrder deliveryOrder = (DeliveryOrder) execution.getVariable(DELIVERY_ORDER);
            DeliveryCompany company = repo.getCompanyByName(deliveryOrder.company);
            String queryURL = UrlHelper.getUrlOrStringEmpty(company) + "order";

            ClientResponse response = WebResourceBuilder.getBuilder(queryURL)
                    .post(ClientResponse.class, deliveryOrder);

            if (response.getStatus() == OK.getStatusCode()) {
                DeliveryOrder order = response.getEntity(DeliveryOrder.class);
                execution.setVariable(DELIVERY_ORDER, order);
                LOGGER.info("DeliveryOrder Status: " + order.getStatus().name());
            }else {
                LOGGER.info("Delivery returned error code: " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe(e.getMessage());
        }
    }
}