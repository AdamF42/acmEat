package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

//TODO: use 3 different model for client, restaurant and delivery
@XmlRootElement
public class RestaurantOrder {
    public String restaurant;
    public List<Dish> dishes;
    public String delivery_time;
    public Integer id;
    public Status status;
    public String from;
    public String to;
}

