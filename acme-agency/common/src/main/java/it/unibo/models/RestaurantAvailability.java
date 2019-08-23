package it.unibo.models;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestaurantAvailability {
    public String restaurant;
    public Boolean is_available;

    @Override
    public String toString() {
        return "RestaurantAvailability{" +
                "restaurantName='" + restaurant + '\'' +
                ", open=" + is_available +
                '}';
    }
}
