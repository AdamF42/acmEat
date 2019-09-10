package it.unibo.controllers;

import it.unibo.ws.generated.Bank;
import it.unibo.ws.generated.BankService;
import it.unibo.ws.generated.GetToken;
import it.unibo.ws.generated.GetTokenResponse;
import it.unibo.models.TokenResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Root resource (exposed at "bank" path)
 */
@Path("bank")
public class BankServiceRest {

    @GET
    @Path("/home/price/{price}/callback_url/{callback_url}")
    @Produces({MediaType.TEXT_HTML})
    public Response home(@PathParam("price") String price,@PathParam("callback_url") String callback_url) throws IOException
    {
        System.out.println("price "+price);
        System.out.println("callback_url "+callback_url);

        String index="";
        File file = new File("src/main/webapp/index.html");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null) {
            index += "\n" + st;
        }
        index+=" <div id=\"price\"  style=\"display: none;\">"+price+"</div>"+" <div id=\"callback\"  style=\"display: none;\">"+callback_url+"</div>";
        return Response.ok(index,MediaType.TEXT_HTML).build();
    }

    @GET
    @Path("/name/{name}/price/{price}")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getToken( @PathParam("name") String name,@PathParam("price") double price){

        //Call jolie service
        Bank bankService = new BankService().getBankServicePort();
        GetToken getToken = new GetToken();
        getToken.setName(name);
        getToken.setAmount(price);
        try {
            GetTokenResponse resp = bankService.getToken(getToken);
            System.out.println(resp.getSid());
            //Send rest response
            TokenResponse json = new TokenResponse();
            json.token = resp.getSid();
            json.user = name;
            json.status = resp.getStatus();

            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        }catch (Exception ex){
            ex.printStackTrace();
            return Response.accepted(ex).build();
        }

    }

}
