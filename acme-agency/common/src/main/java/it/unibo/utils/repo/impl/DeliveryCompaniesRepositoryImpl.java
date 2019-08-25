package it.unibo.utils.repo.impl;

import it.unibo.models.entities.DeliveryCompany;
import it.unibo.utils.repo.DataBase;
import it.unibo.utils.repo.DeliveryCompaniesRepository;

import java.util.List;

public class DeliveryCompaniesRepositoryImpl implements DeliveryCompaniesRepository {
    @Override
    public List<DeliveryCompany> getAllDeliveryCompanies() {
        return DataBase.deliveryCompanies;
    }

    @Override
    public DeliveryCompany getCompanyByName(String name) {

        return  DataBase.deliveryCompanies
                .stream()
                .filter(company-> name.equals(company.name))
                .findAny()
                .orElse(new DeliveryCompany());
    }
}
