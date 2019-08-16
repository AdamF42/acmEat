package it.unibo.utils;


import java.util.Random;

public class Utils {

    public static boolean isAvailable(){
        Random random = new Random();
        return random.nextBoolean();
    }

}
