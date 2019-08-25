package it.unibo.acme;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.DELIVERY_COMPANIES_PROPOSAL;
import static it.unibo.utils.AcmeVariables.SELECTED_COMPANY;

public class CalculateCheaperDeliveryCompany implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(CalculateCheaperDeliveryCompany.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        HashMap<String, Double> deliveryCompanies = (HashMap<String, Double>) delegateExecution.getVariable(DELIVERY_COMPANIES_PROPOSAL);

        if(deliveryCompanies!=null && deliveryCompanies.size()!=0) {
            Map.Entry<String, Double> min = Collections.min(deliveryCompanies.entrySet(), Comparator.comparing(Map.Entry::getValue));
            delegateExecution.setVariable(SELECTED_COMPANY, min.getValue());
            LOGGER.info("MinPrice: <" + min.getKey() + ", " + min.getValue() + ">");
        } else{
            LOGGER.warning("No delivery companies found");
        }
    }
}
