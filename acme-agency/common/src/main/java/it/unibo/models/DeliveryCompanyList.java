package it.unibo.models;

import com.google.gson.annotations.Expose;
import it.unibo.models.entities.DeliveryCompany;

import java.util.ArrayList;

public class DeliveryCompanyList {

    @Expose
    private ArrayList<DeliveryCompany> companies;

    public ArrayList<DeliveryCompany> getCompanies() {
        return this.companies;
    }

    public boolean isEmpty(){
        return this.companies.isEmpty();
    }

    public int size(){
        return this.companies.size();
    }

    public DeliveryCompanyList() {
    }

    public DeliveryCompanyList(ArrayList<DeliveryCompany> companies) {
        this.companies = companies;
    }
}
