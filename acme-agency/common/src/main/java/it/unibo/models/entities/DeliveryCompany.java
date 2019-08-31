package it.unibo.models.entities;


import com.google.gson.annotations.Expose;

public class DeliveryCompany {

    @Expose
    public Double price;

    @Expose
    public String name;

    @Expose
    public String url;

    public DeliveryCompany(){}

    public DeliveryCompany(String name, String url){
        this.name=name;
        this.url=url;
    }

}
