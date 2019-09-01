package it.unibo.models.entities;

import com.google.gson.annotations.Expose;
import it.unibo.models.Dish;

import java.util.List;

public class Restaurant {

    @Expose
    public String name;

    @Expose
    public List<Dish> menu;

    @Expose
    public String city;

    @Expose
    public String url;

    @Expose
    public Boolean is_open;

    public Restaurant(){}

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", menu=" + menu +
                ", city='" + city + '\'' +
                ", url='" + url + '\'' +
                ", is_open=" + is_open +
                '}';
    }

    public Restaurant(String name, List<Dish> menu, String city, String url, Boolean is_open) {
        this.name = name;
        this.menu = menu;
        this.city = city;
        this.url = url;
        this.is_open = is_open;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    public void setIs_open(Boolean is_open) {
        this.is_open = is_open;
    }
}
