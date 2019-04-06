package it.unibo;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeliveryServiceTest {

    private Order order;
    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
//         c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);

        order = new Order();
        order.deliveryTime = "11";
        order.destAddress = "B";
        order.srcAddress = "A";
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }


    @Test
    public void testPlaceOrder() {
        Response responseMsg = target.path("delivery/order").request().post(Entity.json(order));
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        assertTrue(responseMsg.hasEntity());
        PlaceOrderResponse responseOrder = responseMsg.readEntity(PlaceOrderResponse.class);
        assertNotNull(responseOrder);
        assertNotNull(responseOrder.id);

    }


    @Test
    public void testGetOrder() {
        PlaceOrderResponse placeOrderResponse = placeOrder();
        Response responseMsg;


        responseMsg = target.path("delivery/order/" + placeOrderResponse.id).request().get();
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        Order responseOrder = responseMsg.readEntity(Order.class);
        assertNotNull(responseOrder);
        assertEquals(placeOrderResponse.id, responseOrder.id);
        assertEquals(order.deliveryTime, responseOrder.deliveryTime);
        assertEquals(order.srcAddress, responseOrder.srcAddress);
        assertEquals(order.destAddress, responseOrder.destAddress);
        assertEquals(Status.pending, responseOrder.status);
    }

    @Test
    public void testGetOrder_NotExisting() {
        Response responseMsg = target.path("delivery/order/" + 666).request().get();
        assertNotNull(responseMsg);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseMsg.getStatus());
    }

    @Test
    public void testUpdateOrder() {
        PlaceOrderResponse placeOrderResponse = placeOrder();
        Response responseMsg;

        Order responseOrder = getOrder(placeOrderResponse);


        responseOrder.status = Status.available;
        responseOrder.price = 123.1F;


        responseMsg = target.path("delivery/order/" + responseOrder.id).request().put(Entity.json(responseOrder));
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        assertTrue(responseMsg.hasEntity());
        Order updatedOrderResponse = responseMsg.readEntity(Order.class);
        assertNotNull(updatedOrderResponse);
        assertEquals(responseOrder.status, updatedOrderResponse.status);
        assertEquals(responseOrder.price, updatedOrderResponse.price);
    }


    @Test
    public void testConfirmOrder() {
        PlaceOrderResponse placeOrderResponse = placeOrder();

        Order responseOrder = getOrder(placeOrderResponse);

        Response responseMsg = target.path("delivery/order/" + responseOrder.id + "/status/" + Status.confirmed).request().put(Entity.json(""));
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        assertTrue(responseMsg.hasEntity());
        Order updatedOrderResponse = responseMsg.readEntity(Order.class);
        assertNotNull(updatedOrderResponse);
        assertEquals(Status.confirmed, updatedOrderResponse.status);
    }


    @Test
    public void testAbortOrder() {
        PlaceOrderResponse placeOrderResponse = placeOrder();

        Order responseOrder = getOrder(placeOrderResponse);

        Response responseMsg = target.path("delivery/order/" + responseOrder.id + "/status/" + Status.aborted).request().put(Entity.json(""));
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        assertTrue(responseMsg.hasEntity());
        Order updatedOrderResponse = responseMsg.readEntity(Order.class);
        assertNotNull(updatedOrderResponse);
        assertEquals(Status.aborted, updatedOrderResponse.status);
    }


    private Order getOrder(PlaceOrderResponse placeOrderResponse) {
        Response responseMsg;
        responseMsg = target.path("delivery/order/" + placeOrderResponse.id).request().get();
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        Order responseOrder = responseMsg.readEntity(Order.class);
        assertNotNull(responseOrder);
        assertEquals(placeOrderResponse.id, responseOrder.id);
        assertEquals(order.deliveryTime, responseOrder.deliveryTime);
        assertEquals(order.srcAddress, responseOrder.srcAddress);
        assertEquals(order.destAddress, responseOrder.destAddress);
        assertEquals(Status.pending, responseOrder.status);
        return responseOrder;
    }

    private PlaceOrderResponse placeOrder() {
        Response responseMsg = target.path("delivery/order").request().post(Entity.json(order));
        assertNotNull(responseMsg);
        assertEquals(Response.Status.OK.getStatusCode(), responseMsg.getStatus());
        assertTrue(responseMsg.hasEntity());
        PlaceOrderResponse placeOrderResponse = responseMsg.readEntity(PlaceOrderResponse.class);
        assertNotNull(placeOrderResponse);
        assertNotNull(placeOrderResponse.id);
        return placeOrderResponse;
    }

}
