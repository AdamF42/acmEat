package it.unibo.backoffice;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import it.unibo.models.RestaurantAvailability;
import it.unibo.models.Result;
import it.unibo.models.responses.SimpleResponse;
import it.unibo.utils.ApiHttpServlet;
import it.unibo.utils.WebResourceBuilder;

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


@WebServlet("/change-availability")
public class ChangeAvailability extends ApiHttpServlet {

    private Gson g = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String response = "Impossibile aggiornare la disponibilità";
        try {
            String queryUrl = BASE_URL + "/change-availability";
            ClientResponse serviceResponse = WebResourceBuilder.getBuilder(queryUrl).put(ClientResponse.class, g.fromJson(req.getReader(), RestaurantAvailability.class));

            if (serviceResponse.getStatus() == OK.getStatusCode()) {
                SimpleResponse serviceResponseEntity = serviceResponse.getEntity(SimpleResponse.class);
                if (serviceResponseEntity.result.getStatus().equals(Result.SUCCESS)){
                    response = "Disponibilita comunicata con successo";
                } else {
                    response = serviceResponseEntity.result.getMessage();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendResponse(resp,response);
    }
}
