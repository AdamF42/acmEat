package it.unibo.acme;

import it.unibo.models.DeliveryOrder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.ArrayList;
import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.AVAILABLE_DELIVERY_COMPANIES;
import static it.unibo.utils.AcmeVariables.CURRENT_DELIVERY_ORDER;

public class AddToNearDeliveryCompanies implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(CalculateCheaperDeliveryCompany.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        DeliveryOrder order = (DeliveryOrder) delegateExecution.getVariable(CURRENT_DELIVERY_ORDER);
        ArrayList<DeliveryOrder> orderList = (ArrayList<DeliveryOrder>) delegateExecution.getVariable(AVAILABLE_DELIVERY_COMPANIES);
        orderList.add(order);

        delegateExecution.setVariable(AVAILABLE_DELIVERY_COMPANIES, orderList);

    }
}
