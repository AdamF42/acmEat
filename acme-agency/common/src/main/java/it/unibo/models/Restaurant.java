package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Restaurant {

    public String name;
    public List<Dish> menu;
    public String city;
    public String url;

    public Restaurant(){}

    public Restaurant(String name, List<Dish> menu, String city, String url) {
        this.name = name;
        this.menu = menu;
        this.city = city;
        this.url = url;
    }
}
