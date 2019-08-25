package repo;

import it.unibo.models.Order;

import java.util.HashMap;
import java.util.Map;

public class Orders {

    private static Map<Integer, Order> orders = new HashMap<>();

    public int getNextId(){
        return orders.size() + 1;
    }

    public void addOrder(Order order){
        orders.put(order.id, order);
    }

    public Order getOrderById(int id){
        return orders.get(id);
    }


}
