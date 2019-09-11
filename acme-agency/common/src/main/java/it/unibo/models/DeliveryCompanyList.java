package it.unibo.models;

import com.google.gson.annotations.Expose;
import it.unibo.models.entities.DeliveryCompany;

import java.io.Serializable;
import java.util.ArrayList;

public class DeliveryCompanyList implements Serializable {

    @Expose
    private ArrayList<DeliveryCompany> companies;

    public ArrayList<DeliveryCompany> getCompanies() {
        return this.companies;
    }

    public int size(){
        return this.companies.size();
    }

    public DeliveryCompanyList(ArrayList<DeliveryCompany> companies) {
        this.companies = companies;
    }

}
