package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Dish {

    public String name;
    public String price;

    public Dish() { }

    public Dish(String name, String price) {
        this.name = name;
        this.price = price;
    }
}
