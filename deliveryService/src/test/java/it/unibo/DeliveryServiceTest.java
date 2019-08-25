package it.unibo;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import it.unibo.models.Order;
import it.unibo.models.Status;

import it.unibo.utils.Utils;
import mockit.Mock;
import mockit.MockUp;
import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repo.Orders;


import static org.junit.Assert.*;

public class DeliveryServiceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {

        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void CallingAvailability_WithCorrectOrder_ShouldReturnIdAndStatusAvailable() {
        Order order = new Order();
        order.delivery_time = "11";
        order.src_address = "A";
        order.dest_address = "B";
        order.id=0;
        order.status=Status.AVAILABLE;

        new MockUp<Utils>() {
            @Mock
            public boolean isAvailable() {
                return true;
            }
        };

        Response responseMsg = target.path("delivery/availability").request().put(Entity.json(order));
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        assertTrue(responseMsg.hasEntity());
        Order responseOrder = responseMsg.readEntity(Order.class);
        assertNotNull(responseOrder);
        assertNotNull(responseOrder.id);
        assertNotNull(responseOrder.status);
        assertEquals(Status.AVAILABLE,responseOrder.status);
    }

    @Test
    public void CallingAvailability_WithCorrectOrder_ShouldReturnIdAndStatusNotAvailable() {
        Order order = new Order();
        order.delivery_time = "11";
        order.src_address = "A";
        order.dest_address = "B";
        order.status=Status.AVAILABLE;

        new MockUp<Utils>() {
            @Mock
            public boolean isAvailable() {
                return false;
            }
        };

        Response responseMsg = target.path("delivery/availability").request().put(Entity.json(order));
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        assertTrue(responseMsg.hasEntity());
        Order responseOrder = responseMsg.readEntity(Order.class);
        assertNotNull(responseOrder);
        assertNull(responseOrder.id);
        assertNotNull(responseOrder.status);
        assertEquals(Status.NOT_AVAILABLE,responseOrder.status);
    }

    @Test
    public void CallingPlaceOrder_WithAvailableOrder_ShouldReturnStatusAccepted() {
        Order order = new Order();
        order.delivery_time = "11";
        order.src_address = "A";
        order.dest_address = "B";
        order.id=0;
        order.status=Status.AVAILABLE;

        new MockUp<Orders>() {
            @Mock
            public Order getOrderById(int id) {
                return order;
            }
        };

        order.id = 0;
        order.status = Status.AVAILABLE;
        Response responseMsg = target.path("delivery/order").request().post(Entity.json(order));
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        assertTrue(responseMsg.hasEntity());
        Order responseOrder = responseMsg.readEntity(Order.class);
        assertNotNull(responseOrder);
        assertNotNull(responseOrder.id);

    }

    @Test
    public void testGetOrder() {
        Order order = new Order();
        order.delivery_time = "11";
        order.src_address = "A";
        order.dest_address = "B";
        order.id=0;
        order.status=Status.AVAILABLE;

        new MockUp<Orders>() {
            @Mock
            public Order getOrderById(int id) {
                return order;
            }
        };

        Response responseMsg;
        responseMsg = target.path("delivery/order/" + order.id).request().get();
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        Order responseOrder = responseMsg.readEntity(Order.class);
        assertNotNull(responseOrder);
        assertEquals(order.id, responseOrder.id);
        assertEquals(order.delivery_time, responseOrder.delivery_time);
        assertEquals(order.src_address, responseOrder.src_address);
        assertEquals(order.dest_address, responseOrder.dest_address);
        assertEquals(Status.AVAILABLE, responseOrder.status);
    }

    @Test
    public void testConfirmOrder() {

        Order order = new Order();
        order.delivery_time = "11";
        order.src_address = "A";
        order.dest_address = "B";
        order.id=0;
        order.status=Status.AVAILABLE;

        new MockUp<Orders>() {
            @Mock
            public Order getOrderById(int id) {
                return order;
            }
        };

        Response responseMsg = target.path("delivery/order/" + 0 + "/status/" + Status.ACCEPTED).request().put(Entity.json(""));
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        assertTrue(responseMsg.hasEntity());
        Order updatedOrderResponse = responseMsg.readEntity(Order.class);
        assertNotNull(updatedOrderResponse);
        assertEquals(Status.ACCEPTED, updatedOrderResponse.status);
    }


    @Test
    public void testAbortOrder() {

        Order order = new Order();
        order.delivery_time = "11";
        order.src_address = "A";
        order.dest_address = "B";
        order.id=0;
        order.status=Status.AVAILABLE;

        new MockUp<Orders>() {
            @Mock
            public Order getOrderById(int id) {
                return order;
            }
        };

        Response responseMsg = target.path("delivery/order/" + 0 + "/status/" + Status.ABORTED).request().put(Entity.json(""));
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        assertTrue(responseMsg.hasEntity());
        Order updatedOrderResponse = responseMsg.readEntity(Order.class);
        assertNotNull(updatedOrderResponse);
        assertEquals(Status.ABORTED, updatedOrderResponse.status);
    }

}
