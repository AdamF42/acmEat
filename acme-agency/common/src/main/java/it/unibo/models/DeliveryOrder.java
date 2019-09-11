package it.unibo.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;


public class DeliveryOrder implements Serializable {

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

    public Status getStatus() {
        return status;
    }

    @Expose
    public Status status;

    @Expose
    public Double price;

    public Double getPrice() {
        return price;
    }

}

