package it.unibo.acme;

import it.unibo.models.DeliveryOrder;
import it.unibo.models.entities.DeliveryCompany;
import it.unibo.utils.repo.DeliveryCompaniesRepository;
import it.unibo.utils.repo.impl.DeliveryCompaniesRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.ArrayList;
import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.AVAILABLE_DELIVERY_COMPANIES;
import static it.unibo.utils.AcmeVariables.DELIVERY_COMPANIES;

public class GetDeliveryServicesList implements JavaDelegate {
    private final Logger LOGGER = Logger.getLogger(CalculateCheaperDeliveryCompany.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        DeliveryCompaniesRepository repo = new DeliveryCompaniesRepositoryImpl();
        ArrayList<DeliveryCompany> companies = repo.getAllDeliveryCompanies();

        delegateExecution.setVariable(DELIVERY_COMPANIES, companies);
        delegateExecution.setVariable(AVAILABLE_DELIVERY_COMPANIES, new ArrayList<DeliveryOrder>());
    }
}
