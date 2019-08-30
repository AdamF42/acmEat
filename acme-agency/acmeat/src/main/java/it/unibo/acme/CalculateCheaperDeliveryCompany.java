package it.unibo.acme;

import it.unibo.models.DeliveryOrder;
import it.unibo.models.entities.DeliveryOrders;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.DELIVERY_COMPANIES_PROPOSAL;
import static it.unibo.utils.AcmeVariables.DELIVERY_ORDER;

public class CalculateCheaperDeliveryCompany implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(CalculateCheaperDeliveryCompany.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        DeliveryOrders deliveryCompanies =
                (DeliveryOrders) delegateExecution
                        .getVariable(DELIVERY_COMPANIES_PROPOSAL);
        if(deliveryCompanies!=null && deliveryCompanies.size()!=0) {

            DeliveryOrder order = deliveryCompanies.getMinPriceOrder();

            delegateExecution.setVariable(DELIVERY_ORDER, deliveryCompanies.getMinPriceOrder());
            LOGGER.info("Selected delivery company: "+ order.company);

        } else{
            LOGGER.warning("No delivery companies found");
        }
    }
}
