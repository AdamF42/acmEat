package it.unibo.models;

import com.google.gson.annotations.Expose;


public class DeliveryOrder {

    @Expose
    public String company;

    @Expose
    public String src_address;

    @Expose
    public String dest_address;

    @Expose
    public String delivery_time;

    @Expose
    public Integer id;

    @Expose
    public Status status;

    @Expose
    public Double price;

    public Double getPrice() {
        return price;
    }




}

