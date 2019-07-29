package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class DeliveryOrder {

    public String srcAddress;
    public String destAddress;
    public String deliveryTime;
    public Integer id;
    public Status status;
    public Double price;
}

