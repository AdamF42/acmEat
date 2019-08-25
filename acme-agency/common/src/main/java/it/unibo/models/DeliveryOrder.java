package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class DeliveryOrder {
    public String company;
    public String src_address;
    public String dest_address;
    public String delivery_time;
    public Integer id;
    public Status status;
    public Double price;
}

