package it.unibo.utils.repo.impl;

import it.unibo.models.entities.DeliveryCompany;
import it.unibo.utils.repo.DataBase;
import it.unibo.utils.repo.DeliveryCompaniesRepository;

import java.util.List;

public class DeliveryCompaniesRepositoryImpl implements DeliveryCompaniesRepository {


    private DataBase db;

    public DeliveryCompaniesRepositoryImpl(DataBase db) {
        this.db = db;
    }

    public DeliveryCompaniesRepositoryImpl() {
        this.db= new DataBase();
    }

    @Override
    public List<DeliveryCompany> getAllDeliveryCompanies() {
        return db.deliveryCompanies;
    }

    @Override
    public DeliveryCompany getCompanyByName(String name) {

        return  db.deliveryCompanies
                .stream()
                .filter(company-> name.equals(company.name))
                .findAny()
                .orElse(new DeliveryCompany());
    }
}
