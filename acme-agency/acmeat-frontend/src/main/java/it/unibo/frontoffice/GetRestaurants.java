package it.unibo.frontoffice;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import it.unibo.models.RestaurantAvailability;
import it.unibo.models.Result;
import it.unibo.models.responses.GetRestaurantResponse;
import it.unibo.models.responses.SimpleResponse;
import it.unibo.utils.RestClient;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static it.unibo.utils.Services.BASE_URL;
import static javax.ws.rs.core.Response.Status.OK;

@WebServlet("/get-restaurants")
public class GetRestaurants extends HttpServlet {

    private final Gson g = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String city = req.getParameter("city");
        String queryUrl = BASE_URL + "/get-restaurant?city=".concat(city);

        WebResource webResource = new RestClient(queryUrl).getWebResource();
        ClientResponse serviceResponse = webResource
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        String response;

         if (serviceResponse.getStatus() == OK.getStatusCode()){
             GetRestaurantResponse serviceResponseEntity = serviceResponse.getEntity(GetRestaurantResponse.class);
             if(serviceResponseEntity.result.getStatus().equals(Result.SUCCESS)) {
                response = g.toJson(serviceResponseEntity.restaurants);
            }else{
                 //response = "Ristoranti ricevuti con successo";
                 response = serviceResponseEntity.result.getMessage();
             }
        } else {
            response = "Impossibile ricevere ristoranti";
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(response);
        out.flush();
    }
}
