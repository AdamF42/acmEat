package it.unibo.acme;

import it.unibo.models.DeliveryOrder;
import it.unibo.models.DeliveryOrderList;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


import static it.unibo.utils.AcmeVariables.AVAILABLE_DELIVERY_COMPANIES;
import static it.unibo.utils.AcmeVariables.CURRENT_DELIVERY_ORDER;

public class AddToNearDeliveryCompanies implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        DeliveryOrder order = (DeliveryOrder) delegateExecution.getVariable(CURRENT_DELIVERY_ORDER);
        DeliveryOrderList orderList = (DeliveryOrderList) delegateExecution.getVariable(AVAILABLE_DELIVERY_COMPANIES);
        orderList.addOrder(order);
        delegateExecution.setVariable(AVAILABLE_DELIVERY_COMPANIES, orderList);

    }
}
