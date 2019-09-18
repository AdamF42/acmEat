package it.unibo.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class DeliveryOrderList implements Serializable {


    @Expose
    private ArrayList<DeliveryOrder> orders;

    public DeliveryOrderList() {
        this.orders = new ArrayList<>();
    }

    public void addOrder(DeliveryOrder order) {
        this.orders.add(order);
    }

    public ArrayList<DeliveryOrder> getOrders() {
        return orders;
    }

    public boolean isEmpty() {
        return this.orders.size() == 0;
    }

    public DeliveryOrder calculateMinPriceOrder() {
        return this.orders.stream()
                .min(Comparator.comparing(DeliveryOrder::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }
}
