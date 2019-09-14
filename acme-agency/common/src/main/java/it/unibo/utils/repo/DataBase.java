package it.unibo.utils.repo;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.unibo.models.entities.DeliveryCompany;
import it.unibo.models.entities.Restaurant;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.unibo.utils.AcmeVariables.DELIVERY_COMPANY_ONE;
import static it.unibo.utils.AcmeVariables.DELIVERY_COMPANY_TWO;
import static it.unibo.utils.Services.DELIVERY_COMPANY_ONE_URL;
import static it.unibo.utils.Services.DELIVERY_COMPANY_TWO_URL;


public class DataBase {

    private Gson g = new Gson();
    private static final String DATABASE_NAME = "restaurant.json";
    public List<Restaurant> restaurants;
    public ArrayList<DeliveryCompany> deliveryCompanies;

    public DataBase() {

        try (JsonReader reader = new JsonReader(new FileReader(DATABASE_NAME))) {
            this.restaurants = Arrays.asList(g.fromJson(reader, Restaurant[].class));
            this.deliveryCompanies = new ArrayList<>(Arrays.asList(
                    new DeliveryCompany(DELIVERY_COMPANY_ONE, DELIVERY_COMPANY_ONE_URL,"Piazza Giuseppe Verdi, 40126 Bologna BO"),
                    new DeliveryCompany(DELIVERY_COMPANY_TWO, DELIVERY_COMPANY_TWO_URL, "Via Teodosio, 60, 20131 Milano MI"),
                    //TODO: to be removed...
                    new DeliveryCompany("debug", DELIVERY_COMPANY_TWO_URL,"Piazza Giuseppe Verdi, 40126 Bologna BO")
            ));
        } catch (Exception e) {
            e.printStackTrace();
            this.restaurants = new ArrayList<>();
            this.deliveryCompanies = new ArrayList<>();
        }
    }

    public void saveChanges() {

        try (JsonWriter writer = new JsonWriter(new FileWriter(DATABASE_NAME))) {
            g.toJson(g.toJsonTree(this.restaurants), writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
