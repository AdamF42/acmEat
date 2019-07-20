package it.unibo.controllers;

import it.unibo.models.Status;
import it.unibo.models.Order;
import it.unibo.models.PlaceOrderResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Root resource (exposed at "delivery" path)
 */
@Path("delivery")
public class DeliveryService {

    Random random = new Random();
    static Map<Integer, Order> orders = new HashMap<>();

    @POST
    @Path("/order")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response sendOrder(Order order) {

        int nextId = orders.size() + 1;

        order.id = nextId;
        order.status = Status.pending;
        orders.put(nextId, order);
        PlaceOrderResponse json = new PlaceOrderResponse();
        json.id = order.id;

        return Response.ok(json, MediaType.APPLICATION_JSON).build();

    }

    @GET
    @Path("/order/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getOrder( @PathParam("id") int id){

        Order order = orders.get(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(order, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/order/{id}/status/{status}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response updateOrderStatus( @PathParam("id") int id, @PathParam("status") Status status){

        Order order = orders.get(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        order.status = status;

        return Response.ok(order, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/order/{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response updateOrder( @PathParam("id") int id, Order order){

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        orders.put(id,order);

        return Response.ok(order, MediaType.APPLICATION_JSON).build();
    }

}
