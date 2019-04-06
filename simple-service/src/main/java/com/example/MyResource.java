package com.example;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
    String objectToReturn = "{ key1: 'value1', key2: 'value2' }";


    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }


    @POST
    @Path("/delivery")
    @Consumes("application/json")
    @Produces("application/json")

    public Response postClichedMessage(String message) {


        String json = "{ \"id\": \"123\", \"status\" : \"SUCCESS\", \"message\": \"\", \"price\": \"5€\" }";
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

}
