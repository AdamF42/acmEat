package it.unibo.acme;

import it.unibo.models.DeliveryOrder;
import it.unibo.models.DeliveryOrderList;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.AVAILABLE_DELIVERY_COMPANIES;
import static it.unibo.utils.AcmeVariables.DELIVERY_ORDER;

public class CalculateCheaperDeliveryCompany implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(CalculateCheaperDeliveryCompany.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) {

        DeliveryOrderList deliveryCompanies =
                (DeliveryOrderList) delegateExecution
                        .getVariable(AVAILABLE_DELIVERY_COMPANIES);

        if (deliveryCompanies == null || deliveryCompanies.isEmpty()) {
            LOGGER.warning("No delivery companies found");
            return;
        }

        DeliveryOrder order = deliveryCompanies.calculateMinPriceOrder();

        delegateExecution.setVariable(DELIVERY_ORDER, order);
        LOGGER.info("Selected delivery company: " + order.company);
    }
}
