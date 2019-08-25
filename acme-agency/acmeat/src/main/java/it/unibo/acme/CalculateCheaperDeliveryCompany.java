package it.unibo.acme;

import com.google.gson.Gson;
import it.unibo.models.DeliveryOrder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.*;
import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.DELIVERY_COMPANIES_PROPOSAL;
import static it.unibo.utils.AcmeVariables.DELIVERY_ORDER;

public class CalculateCheaperDeliveryCompany implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(CalculateCheaperDeliveryCompany.class.getName());
    private Gson g = new Gson();

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        HashMap<String, String> deliveryCompanies =
                (HashMap<String, String>) delegateExecution
                        .getVariable(DELIVERY_COMPANIES_PROPOSAL);
        if(deliveryCompanies!=null && deliveryCompanies.size()!=0) {

            String order = deliveryCompanies
                    .entrySet()
                    .stream()
                    .min(Comparator.comparing(t -> g.fromJson(t.getValue(), DeliveryOrder.class).getPrice()))
                    .orElseThrow(NoSuchElementException::new)
                    .getValue();

            delegateExecution.setVariable(DELIVERY_ORDER, order);
            LOGGER.info("Selected delivery company: "+order);

        } else{
            LOGGER.warning("No delivery companies found");
        }
    }
}
