package it.unibo.utils;

import it.unibo.models.entities.DeliveryCompany;
import it.unibo.models.entities.Restaurant;

public class UrlHelper {

    public static String getUrlOrStringEmpty(DeliveryCompany company) {
        return company != null ? company.url : "";
    }

    public static String getUrlOrStringEmpty(Restaurant restaurant) {
        return restaurant != null ? restaurant.url : "";
    }

}
