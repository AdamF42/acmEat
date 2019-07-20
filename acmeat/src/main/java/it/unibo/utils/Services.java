package it.unibo.utils;

import java.util.Random;

public class Services {

    public static String DELIVERY_SERVICE_URL="http://localhost:18080/delivery/";

    public static String restaurantServiceUrl="http://localhost:5000/restaurant/";

    public static String GIS_SERVICE_URL ="http://localhost:7778/";

    public static String bankServiceUrl=" ";

    public static String getURLforGis(String from,String to){return "?from="+from+"&to="+to;}


    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}

