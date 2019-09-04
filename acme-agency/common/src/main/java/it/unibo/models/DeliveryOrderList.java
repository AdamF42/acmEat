package it.unibo.models;

import com.google.gson.annotations.Expose;

import java.util.*;

public class DeliveryOrderList {

    @Expose
    private ArrayList<DeliveryOrder> orders;

    public DeliveryOrderList() {
        this.orders = new ArrayList<>();
    }

    public void addOrder(DeliveryOrder order) {
        this.orders.add(order);
    }

    public boolean isEmpty(){
        return this.orders.size()==0;
    }

    public DeliveryOrder calculateMinPriceOrder(){
        return this.orders.stream()
                .min(Comparator.comparing(DeliveryOrder::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }
}
