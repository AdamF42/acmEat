package it.unibo.acme;

import it.unibo.models.DeliveryOrder;
import it.unibo.models.DeliveryOrderList;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.*;

public class CalculateCheaperDeliveryCompany implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(CalculateCheaperDeliveryCompany.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        ArrayList<DeliveryOrder> deliveryCompanies =
                (ArrayList<DeliveryOrder>) delegateExecution
                        .getVariable(AVAILABLE_DELIVERY_COMPANIES);

        if (deliveryCompanies == null || deliveryCompanies.isEmpty()) {
            LOGGER.warning("No delivery companies found");
            return;
        }

        //TODO: use DeliveryOrderList
        DeliveryOrder order = deliveryCompanies.stream()
                .min(Comparator.comparing(DeliveryOrder::getPrice))
                .orElseThrow(NoSuchElementException::new);

        delegateExecution.setVariable(DELIVERY_ORDER, order);
        LOGGER.info("Selected delivery company: " + order.company);
    }
}
