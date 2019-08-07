package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Restaurant {

    String name;
    List<Dish> menu;
    public String city;

    public Restaurant(String name, List<Dish> menu, String city) {
        this.name = name;
        this.menu = menu;
        this.city = city;
    }
}
