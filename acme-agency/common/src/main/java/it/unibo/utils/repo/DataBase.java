package it.unibo.utils.repo;

import it.unibo.models.entities.DeliveryCompany;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.unibo.utils.AcmeVariables.DELIVERY_COMPANY_ONE;
import static it.unibo.utils.AcmeVariables.DELIVERY_COMPANY_TWO;
import static it.unibo.utils.Services.*;

public class DataBase {

    public static List<DeliveryCompany> deliveryCompanies = new ArrayList<DeliveryCompany>(Arrays.asList(
        new DeliveryCompany(DELIVERY_COMPANY_ONE, DELIVERY_COMPANY_ONE_URL),
        new DeliveryCompany(DELIVERY_COMPANY_TWO, DELIVERY_COMPANY_TWO_URL),
        //TODO: to be removed...
        new DeliveryCompany("debug", DELIVERY_COMPANY_TWO_URL)
    ));



}
