package it.unibo;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Order {

    public String srcAddress;
    public String destAddress;
    public String deliveryTime;
    public Integer id;
    public Status status;
    public Float price;
}

