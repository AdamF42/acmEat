package it.unibo.models.entities;

import com.google.gson.annotations.Expose;
import it.unibo.models.DeliveryOrder;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class DeliveryOrders {

    @Expose
    private List<DeliveryOrder> orders;

    public DeliveryOrders() {
        orders = new LinkedList<>();
    }

    public void addOrder(DeliveryOrder order) {
        orders.add(order);
    }

    public boolean isEmpty(){
        return orders.size()==0;
    }

    public DeliveryOrder getMinPriceOrder(){
        return orders.stream()
                .min(Comparator.comparing(DeliveryOrder::getPrice))
                .orElseThrow(NoSuchElementException::new);
    }
}
