package it.unibo.utils.repo;

import it.unibo.models.entities.DeliveryCompany;

import java.util.ArrayList;

public interface DeliveryCompaniesRepository {

    ArrayList<DeliveryCompany> getAllDeliveryCompanies();

    DeliveryCompany getCompanyByName(String name);
}
