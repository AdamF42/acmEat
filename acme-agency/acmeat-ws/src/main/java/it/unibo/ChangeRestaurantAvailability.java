package it.unibo;

import camundajar.com.google.gson.Gson;
import camundajar.com.google.gson.stream.JsonReader;
import it.unibo.models.responses.ResponseChangingConfirmation;
//import it.unibo.models.ResponseGetRestaurant;
import it.unibo.models.RestaurantAvailability;
import it.unibo.models.Result;
import it.unibo.models.entities.Restaurant;
import it.unibo.utils.repo.RestaurantRepository;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static it.unibo.utils.AcmeMessages.CHANGE_RESTAURANT_AVAILABILITY;

@WebServlet("/change-rest-availability")
public class ChangeRestaurantAvailability extends HttpServlet {

    @Inject
    ProcessEngine processEngine;

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Gson g = new Gson();
        RuntimeService service = processEngine.getRuntimeService();

        System.out.println("Restaurant comunicated availability");

        /*JsonReader reader = new JsonReader(new FileReader("restaurant.json"));

        Restaurant[] reviews = new Gson().fromJson(reader, Restaurant[].class);
        List<Restaurant> asList = Arrays.asList(reviews);*/

        RestaurantAvailability availability = g.fromJson(req.getReader(), RestaurantAvailability.class);
        String restaurantName= availability.restaurant;


        RestaurantRepository repo = new RestaurantRepositoryImpl();
        repo.setOpening(availability);

        service.createMessageCorrelation(CHANGE_RESTAURANT_AVAILABILITY)
                .correlate();

        // return confirmation
        ResponseChangingConfirmation response = new ResponseChangingConfirmation();
        response.setResult(fillResult());
        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(response));
        out.flush();

        //R/1970-01-01T06:00:00Z/PT24H
        //Message_0l6ef0l
        //R/1970-01-01T19:00:00Z/PT24H
    }

    private Result fillResult(){
        Result result = new Result();
        result.setStatus("success");
        result.setMessage("Your request of availability change has been received");
        return result;
    }


}
