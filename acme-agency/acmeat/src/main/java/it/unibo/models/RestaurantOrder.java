package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class RestaurantOrder {
    public String content;
    public String deliveryTime;
    public Integer id;
    public Status status;
}

