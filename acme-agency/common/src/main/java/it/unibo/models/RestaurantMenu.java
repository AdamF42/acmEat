package it.unibo.models;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class RestaurantMenu {

    public String restaurant;
    public List<Dish> menu;

}
