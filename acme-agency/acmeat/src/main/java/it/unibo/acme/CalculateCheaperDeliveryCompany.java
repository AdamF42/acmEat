package it.unibo.acme;

import it.unibo.LoggerDelegate;
import it.unibo.models.DeliveryCompany;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.DELIVERY_COMPANIES_PROPOSAL;
import static it.unibo.utils.AcmeVariables.SELECTED_COMPANY;

public class CalculateCheaperDeliveryCompany implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        Map<String, Double> deliveryCompanies = (Map<String, Double>) delegateExecution.getVariable(DELIVERY_COMPANIES_PROPOSAL);

        Map.Entry<String, Double> min = Collections.min(deliveryCompanies.entrySet(), Comparator.comparing(Map.Entry::getValue));

        DeliveryCompany selctedCompany = new DeliveryCompany();
        selctedCompany.name=min.getKey();
        selctedCompany.price=Double.toString(min.getValue());

        delegateExecution.setVariable(SELECTED_COMPANY, selctedCompany);

        LOGGER.info("MinPrice: <"+ min.getKey()+", "+min.getValue()+">");
    }
}
