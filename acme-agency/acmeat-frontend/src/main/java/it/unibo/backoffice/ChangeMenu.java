package it.unibo.backoffice;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import it.unibo.models.RestaurantMenu;
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


@WebServlet("/change-menu")
public class ChangeMenu extends ApiHttpServlet {

    private Gson g = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String response = "Impossibile aggiornare il menu";


        try {
            String url = BASE_URL + "/change-menu";
            ClientResponse serviceResponse = WebResourceBuilder.getBuilder(url).post(ClientResponse.class, g.fromJson(req.getReader(), RestaurantMenu.class));
            if (serviceResponse.getStatus() == OK.getStatusCode()) {
                SimpleResponse serviceResponseEntity = serviceResponse.getEntity(SimpleResponse.class);
                if (serviceResponseEntity.result.getStatus().equals(Result.SUCCESS)) {
                    response = "Variazione menu comunicata con successo";
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
