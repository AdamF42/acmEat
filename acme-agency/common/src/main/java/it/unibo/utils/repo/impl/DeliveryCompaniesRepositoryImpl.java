package it.unibo.utils.repo.impl;

import it.unibo.models.DeliveryCompany;
import it.unibo.utils.repo.DataBase;
import it.unibo.utils.repo.DeliveryCompaniesRepository;

import java.util.ArrayList;
import java.util.List;

public class DeliveryCompaniesRepositoryImpl implements DeliveryCompaniesRepository {
    public List<DeliveryCompany> getAllDeliveryCompanies() {
        return DataBase.deliveryCompanies;
    }
}
