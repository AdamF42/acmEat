package it.unibo.utils.repo;

import it.unibo.models.entities.DeliveryCompany;

import java.util.List;

public interface DeliveryCompaniesRepository {
    List<DeliveryCompany> getAllDeliveryCompanies();
    DeliveryCompany getCompanyByName(String name);
}
