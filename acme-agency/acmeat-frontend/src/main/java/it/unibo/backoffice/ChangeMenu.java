package it.unibo.backoffice;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import it.unibo.models.RestaurantMenu;
import it.unibo.models.Result;
import it.unibo.models.responses.SimpleResponse;
import it.unibo.utils.RestClient;

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


@WebServlet("/change-menu")
public class ChangeMenu extends HttpServlet {

    private Gson g = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String url = BASE_URL + "/change-menu";
        WebResource webResource = new RestClient(url).getWebResource();
        ClientResponse serviceResponse = webResource
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, g.fromJson(req.getReader(), RestaurantMenu.class));

        String response;
        if (serviceResponse.getStatus() == OK.getStatusCode()
                && serviceResponse
                .getEntity(SimpleResponse.class)
                .result.getStatus().equals(Result.SUCCESS)) {
            response = "Menu aggiornato con successo";
        } else {
            response = "Impossibile aggiornare il menu";
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(response);
        out.flush();
    }
}
