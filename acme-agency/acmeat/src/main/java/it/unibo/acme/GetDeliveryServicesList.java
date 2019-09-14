package it.unibo.acme;

import it.unibo.models.DeliveryCompanyList;
import it.unibo.models.DeliveryOrderList;
import it.unibo.utils.repo.DeliveryCompaniesRepository;
import it.unibo.utils.repo.impl.DeliveryCompaniesRepositoryImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static it.unibo.utils.AcmeVariables.AVAILABLE_DELIVERY_COMPANIES;
import static it.unibo.utils.AcmeVariables.DELIVERY_COMPANIES;

public class GetDeliveryServicesList implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        DeliveryCompaniesRepository repo = new DeliveryCompaniesRepositoryImpl();
        DeliveryCompanyList companies = new DeliveryCompanyList(repo.getAllDeliveryCompanies());

        delegateExecution.setVariable(DELIVERY_COMPANIES, companies);
        delegateExecution.setVariable(AVAILABLE_DELIVERY_COMPANIES, new DeliveryOrderList());
    }
}
