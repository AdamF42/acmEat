package it.unibo.backoffice;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import it.unibo.models.RestaurantAvailability;
import it.unibo.models.Result;
import it.unibo.models.responses.SimpleResponse;
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
public class ChangeAvailability extends HttpServlet {

    private Gson g = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String queryUrl = BASE_URL + "/change-availability";

        ClientResponse serviceResponse = WebResourceBuilder.getBuilder(queryUrl).put(ClientResponse.class, g.fromJson(req.getReader(), RestaurantAvailability.class));

        String response;
        if (serviceResponse.getStatus() == OK.getStatusCode()
                && serviceResponse
                .getEntity(SimpleResponse.class)
                .result.getStatus().equals(Result.SUCCESS)) {
            response = "Disponibilita comunicata con successo";
        } else {
            response = "Impossibile aggiornare la disponibilità";
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(response);
        out.flush();
    }
}
