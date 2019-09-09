package it.unibo.models.entities;


import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "deliveryCompany")
@XmlType(name = "deliveryCompany")
public class DeliveryCompany implements Serializable {

    @Expose
    @XmlElement(name = "name", nillable = true)
    public String name;

    @Expose
    @XmlElement(name = "url", nillable = true)
    public String url;

    @Expose
    @XmlElement(name = "address", nillable = true)
    public String address;

    public DeliveryCompany() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public DeliveryCompany(String name, String url, String address) {
        this.name = name;
        this.url = url;
        this.address = address;
    }

}
