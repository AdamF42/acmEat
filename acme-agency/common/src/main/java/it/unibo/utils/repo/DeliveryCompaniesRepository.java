package it.unibo.utils.repo;

import it.unibo.models.entities.DeliveryCompany;

import java.util.ArrayList;

public interface DeliveryCompaniesRepository extends Repository{

    ArrayList<DeliveryCompany> getAllDeliveryCompanies();

    DeliveryCompany getCompanyByName(String name);
}
