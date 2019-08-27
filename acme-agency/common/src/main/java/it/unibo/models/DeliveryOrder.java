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

    public Double getPrice() {
        return price;
    }

    public Double price;

    @XmlRootElement
    public static class Dish {

        public String name;
        public String price;

        public Dish() { }

        public Dish(String name, String price) {
            this.name = name;
            this.price = price;
        }
    }
}

