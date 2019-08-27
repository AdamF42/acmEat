package it.unibo.utils.repo;

import it.unibo.models.DeliveryOrder;
import it.unibo.models.entities.DeliveryCompany;
import it.unibo.models.entities.Restaurant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.unibo.utils.AcmeVariables.DELIVERY_COMPANY_ONE;
import static it.unibo.utils.AcmeVariables.DELIVERY_COMPANY_TWO;
import static it.unibo.utils.Services.*;

public class DataBase {
    public static List<Restaurant> restaurants = new ArrayList<>(Arrays.asList(
        new Restaurant("Ciccio",
            Arrays.asList(
                    new DeliveryOrder.Dish("Lasagne","5"),
                    new DeliveryOrder.Dish("Pizza","3")
            ),
            "Bologna",
            CICCIO_URL
        ),
        new Restaurant("Yoma",
            Arrays.asList(
                    new DeliveryOrder.Dish("Ravioli","5"),
                    new DeliveryOrder.Dish("Sushi","3")
            ),
            "Bologna",
            YOMA_URL
        ),
        new Restaurant("debug",
                Arrays.asList(
                        new DeliveryOrder.Dish("Ravioli","5"),
                        new DeliveryOrder.Dish("Sushi","3")
                ),
                "Bologna",
                YOMA_URL
        )
    ));

    public static List<DeliveryCompany> deliveryCompanies = new ArrayList<DeliveryCompany>(Arrays.asList(
        new DeliveryCompany(DELIVERY_COMPANY_ONE, DELIVERY_COMPANY_ONE_URL),
        new DeliveryCompany(DELIVERY_COMPANY_TWO, DELIVERY_COMPANY_TWO_URL),
        //TODO: to be removed...
        new DeliveryCompany("debug", DELIVERY_COMPANY_TWO_URL)
    ));
}
