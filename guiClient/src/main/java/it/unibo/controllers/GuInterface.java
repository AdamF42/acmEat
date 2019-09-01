package it.unibo.controllers;


import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static javax.ws.rs.core.Response.Status.ACCEPTED;
import static javax.ws.rs.core.Response.Status.OK;


//parte 2

/**
 * Root resource (exposed at "acmeat" path)
 */
@Path("acmeat")
public class GuInterface {

    @GET
    @Path("/home")
    @Produces({MediaType.TEXT_HTML})
    public Response home() throws IOException
    {

        String a="";
        File file = new File("src/main/java/it/unibo/gui/index.html");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null)
            a+="\n"+st;

        return Response.ok(a,MediaType.TEXT_HTML).build();
    }

    @GET
    @Path("/home-restaurant")
    @Produces({MediaType.TEXT_HTML})
    public Response homeRestaurant() throws IOException
    {

        String a="";
        File file = new File("src/main/java/it/unibo/gui/indexRestaurant.html");

        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        while ((st = br.readLine()) != null)
            a+="\n"+st;

        return Response.ok(a,MediaType.TEXT_HTML).build();
    }


    @GET
    @Path("/get-restaurant/{city}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCity(@Context Request request, @PathParam("city") String city) throws IOException
    {
        System.out.println("Requested restaurants in city "+city);
        String url = "http://localhost:8080/acmeat-ws/get-restaurant?city=".concat(city);

        Session session = request.getSession(true);
        if(!session.isNew()) {
            session.setValid(false);
            session = request.getSession(true);
        }

        String acmeCookie = (String) session.getAttribute("Cookie");

        URL acmeRequest = new URL(url);
        HttpURLConnection con = (HttpURLConnection) acmeRequest.openConnection();
        con.setRequestProperty("Cookie",acmeCookie==null?"":acmeCookie);
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //mando al client in cookie generato da acmeat-ws: Set-Cookie
        String cookie = con.getHeaderField("Set-Cookie");
        session.setAttribute("Cookie",cookie);

        return  Response.ok(response.toString(),MediaType.APPLICATION_JSON).cookie(new NewCookie("Set-Cookie",cookie)).build();
    }

    @POST
    @Path("/send-order")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response sendOrder(@Context Request request, String order) throws IOException
    {
        System.out.println("Received order "+order);
        String url = "http://localhost:8080/acmeat-ws/send-order";
        Session session = request.getSession(false);
        String acmeCookie = (String) session.getAttribute("Cookie");

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        com.sun.jersey.api.client.Client client = Client.create(clientConfig);
        WebResource webResourcePost = client.resource(url);
        ClientResponse risp =  webResourcePost
                .accept("application/json")
                .type("application/json")
                .post(ClientResponse.class, order);
        if (risp.getStatus() ==  OK.getStatusCode()) {
            return Response.ok("Ordine comunicato con successo",MediaType.APPLICATION_JSON).build();
        }

        return Response.ok("Ordine non comunicato con successo",MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/cancel")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response cancelOrder() throws IOException
    {

        String url = "http://localhost:8080/acmeat-ws/abort";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("PUT");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'PUT' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        String c= con.getHeaderField("Set-Cookie");
        System.out.println("Cookie : " + c);

        //mandare response entity in formato json
        return Response.ok("Il tuo ordine è stato annullato",MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/change-availability")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response changeAvalability(@Context Request request, String new_avalability) throws IOException
    {
        System.out.println("Received new avalability "+ new_avalability);

        //TODO: chiamata ad acme-ws
        String url = "http://localhost:8080/acmeat-ws/change-rest-availability";
        //Session session = request.getSession(false);
        //String acmeCookie = (String) session.getAttribute("Cookie");

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        com.sun.jersey.api.client.Client client = Client.create(clientConfig);
        WebResource webResourcePost = client.resource(url);
        System.out.println("here");
        ClientResponse risp =  webResourcePost
                .accept("application/json")
                .type("application/json")
                .put(ClientResponse.class, new_avalability);

        if (risp.getStatus() ==  OK.getStatusCode()) {
            System.out.println("her2");
            return Response.ok("Disponibilita comunicata con successo",MediaType.APPLICATION_JSON).build();
        }
        System.out.println("her3" + risp.toString());
        return Response.ok("Cambio di disponibilita non comunicato con successo",MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/change-menu")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response changeMenu(@Context Request request, String new_menu) throws IOException
    {
        System.out.println("Received new menu "+ new_menu);

        //TODO: chiamata ad acme-ws

        /*String url = "http://localhost:8080/acmeat-ws/send-order";
        Session session = request.getSession(false);
        String acmeCookie = (String) session.getAttribute("Cookie");

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        com.sun.jersey.api.client.Client client = Client.create(clientConfig);
        WebResource webResourcePost = client.resource(url);
        ClientResponse risp =  webResourcePost
                .accept("application/json")
                .type("application/json")
                .post(ClientResponse.class, order);
        if (risp.getStatus() ==  OK.getStatusCode()) {
            return Response.ok("Ordine comunicato con successo",MediaType.APPLICATION_JSON).build();
        }
        */
        return Response.ok("Cambio di menu non comunicato con successo",MediaType.APPLICATION_JSON).build();
    }
}
