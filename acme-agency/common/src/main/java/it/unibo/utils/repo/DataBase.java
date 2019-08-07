package it.unibo.utils.repo;

import it.unibo.models.Dish;
import it.unibo.models.Restaurant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBase {
    public static List<Restaurant> restaurants = new ArrayList<Restaurant>(Arrays.asList(
        new Restaurant("Ciccio",
                Arrays.asList(
                        new Dish("Lasagne","5"),
                        new Dish("Pizza","3")
                ),
                "Bologna"
                ),
        new Restaurant("Yoma",
            Arrays.asList(
                    new Dish("Ravioli","5"),
                    new Dish("Sushi","3")
            ),
            "Bologna")
    ));
}
