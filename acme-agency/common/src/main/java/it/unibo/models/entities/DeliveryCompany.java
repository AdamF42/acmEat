package it.unibo.models.entities;


import com.google.gson.annotations.Expose;

import java.io.Serializable;


public class DeliveryCompany implements Serializable {

    @Expose
    public String name;

    @Expose
    public String url;

    @Expose
    public String address;

    public DeliveryCompany() {
    }

    public DeliveryCompany(String name, String url, String address) {
        this.name = name;
        this.url = url;
        this.address = address;
    }
}
