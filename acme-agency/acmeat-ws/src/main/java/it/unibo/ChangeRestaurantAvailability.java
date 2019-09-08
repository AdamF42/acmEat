package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.responses.Response;
import it.unibo.models.factory.ResponseFactory;
//import it.unibo.models.ResponseGetRestaurant;
import it.unibo.models.RestaurantAvailability;
import it.unibo.utils.repo.RestaurantRepository;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;
import org.camunda.bpm.engine.ProcessEngine;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static it.unibo.utils.AcmeMessages.CHANGE_RESTAURANT_AVAILABILITY;

@WebServlet("/change-rest-availability")
public class ChangeRestaurantAvailability extends HttpServlet {

    @Inject
    ProcessEngine processEngine;

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Gson g = new Gson();
        ResponseFactory responseFactory = new ResponseFactory();
        RestaurantAvailability availability = g.fromJson(req.getReader(), RestaurantAvailability.class);

        RestaurantRepository repo = new RestaurantRepositoryImpl();
        repo.addOrUpdateOpeningTime(availability);

        processEngine.getRuntimeService()
                .createMessageCorrelation(CHANGE_RESTAURANT_AVAILABILITY)
                .correlate();

        Response response = responseFactory
                .createSuccessResponse();
        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(response));
        out.flush();

    }

}
