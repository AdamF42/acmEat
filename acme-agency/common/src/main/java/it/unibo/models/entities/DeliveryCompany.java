package it.unibo.models.entities;


import com.google.gson.annotations.Expose;


public class DeliveryCompany {

    @Expose
    public String name;

    @Expose
    public String url;

    @Expose
    public String address;

    public DeliveryCompany() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeliveryCompany(String name, String url, String address) {
        this.name = name;
        this.url = url;
        this.address = address;
    }

    @Override
    public String toString() {
        return "DeliveryCompany{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
