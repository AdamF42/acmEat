package it.unibo.controllers;

import it.unibo.ws.generated.Bank;
import it.unibo.ws.generated.BankService;
import it.unibo.ws.generated.GetToken;
import it.unibo.ws.generated.GetTokenResponse;
import it.unibo.models.TokenResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "bank" path)
 */
@Path("bank")
public class BankServiceRest {

    @GET
    @Path("/name/{name}/price/{price}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getToken( @PathParam("name") String name,@PathParam("price") double price){

        //Call jolie service
        Bank bankService = new BankService().getBankServicePort();
        GetToken getToken = new GetToken();
        getToken.setName(name);
        getToken.setAmount((double) price);
        GetTokenResponse resp = bankService.getToken(getToken);
        System.out.println(resp.getSid());
        //Send rest response
        TokenResponse json = new TokenResponse();
        json.token = resp.getSid();
        json.user = name;
        json.price = price;

        return Response.ok(json, MediaType.APPLICATION_JSON).build();


    }


}
