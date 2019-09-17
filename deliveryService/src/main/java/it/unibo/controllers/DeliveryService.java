package it.unibo.controllers;

import it.unibo.models.Order;
import it.unibo.models.Status;
import repo.Orders;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Random;

import static it.unibo.utils.Utils.isAvailable;


@Path("delivery")
public class DeliveryService {

    private Orders orders = new Orders();
    private Random rand = new Random();


    @POST
    @Path("/availability")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response GetAvailability(Order order) {
        if (!isValidOrderRequest(order)) {
            //TODO: use specific msg...avoid 404
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (isAvailable() || order.company.equals("debug")) {
            order.id = orders.getNextId();
            order.status = Status.AVAILABLE;
            order.price = rand.nextDouble();
            orders.addOrder(order);
        } else {
            order.status = Status.NOT_AVAILABLE;
        }
        return Response.ok(order, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/order")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response sendOrder(Order order) {

        if (order == null || order.id == null) {
            //TODO: use specific msg...avoid 404
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Order dbOrder = orders.getOrderById(order.id);

        if (dbOrder == null) {
            //TODO: use specific msg...avoid 404
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        dbOrder.checkAndSetAcceptedStatus();
        orders.addOrder(dbOrder);

        return Response.ok(dbOrder, MediaType.APPLICATION_JSON).build();

    }

    @GET
    @Path("/order/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOrder(@PathParam("id") int id) {

        Order order = orders.getOrderById(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(order, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/order/{id}/status/{status}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateOrderStatus(@PathParam("id") int id, @PathParam("status") Status status) {

        Order order = orders.getOrderById(id);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        order.status = status;
        orders.addOrder(order);

        return Response.ok(order, MediaType.APPLICATION_JSON).build();
    }

    private static boolean isValidOrderRequest(Order order) {
        return order != null
                && order.company != null
                && order.src_address != null
                && order.dest_address != null
                && order.delivery_time != null;
    }
}
