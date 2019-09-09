package it.unibo.frontoffice;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.Result;
import it.unibo.models.responses.GetRestaurantResponse;
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

@WebServlet("/send-order")
public class SendOrder extends HttpServlet {

    private final Gson g = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String queryUrl = BASE_URL + "/send-order";

        WebResource webResource = new RestClient(queryUrl).getWebResource();
        ClientResponse serviceResponse = webResource
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, g.fromJson(req.getReader(), RestaurantOrder.class));

        String response;
        if (serviceResponse.getStatus() == OK.getStatusCode()){
            GetRestaurantResponse serviceResponseEntity = serviceResponse.getEntity(GetRestaurantResponse.class);
            if(serviceResponseEntity.result.getStatus().equals(Result.SUCCESS)) {
                response = "Ordine confermato con successo";
            }else{
                response = serviceResponseEntity.result.getMessage();
            }
        } else {
            response = "Impossibile inviare l'ordine";
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(response);
        out.flush();
    }

}
