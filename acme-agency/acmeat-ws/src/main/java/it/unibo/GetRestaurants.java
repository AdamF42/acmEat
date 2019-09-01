package it.unibo;

import camundajar.com.google.gson.Gson;
import camundajar.com.google.gson.GsonBuilder;
import it.unibo.models.RestaurantList;
import it.unibo.models.responses.GetRestaurantResponse;
import it.unibo.models.entities.Restaurant;
import it.unibo.models.Result;
import it.unibo.models.responses.GetRestaurantResponseOutOfTime;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.weld.event.Status;
import org.joda.time.DateTime;

import static it.unibo.utils.AcmeVariables.PROCESS_ID;
import static it.unibo.utils.AcmeMessages.START_MESSAGE;


@WebServlet("/get-restaurant")
public class GetRestaurants extends HttpServlet {

    @Inject
    private ProcessEngine processEngine;
    
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RuntimeService service = processEngine.getRuntimeService();
        Map<String, Object> cityVariable = new HashMap<>();
        String cityParam = "city";
        cityVariable.put(cityParam,req.getParameter(cityParam));
        String processInstanceId = service
                .startProcessInstanceByMessage(START_MESSAGE, cityVariable)
                .getProcessInstanceId();

        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        Gson g = builder.create();

        DateTime dt = new DateTime();  // current time
        int hours = dt.getHourOfDay(); // gets hour of day

        // TODO: to be removed
        if((hours>=10) && (hours <=23)){ // access in time
            HttpSession session = req.getSession(true);

            if (!session.isNew()) {
                LOGGER.warn("Client with active session requested new process");
                session.invalidate();
                session = req.getSession(true);
            }

            session.setAttribute(PROCESS_ID, processInstanceId);
            LOGGER.info("Started process instance with id: {}",processInstanceId);


            GetRestaurantResponse response = new GetRestaurantResponse();
            RestaurantList restaurants = g.fromJson((String) service.getVariables(processInstanceId).get("restaurants"),RestaurantList.class);
            response.setRestaurants(restaurants);
            response.setResult(fillResult(!restaurants.isEmpty()));
            PrintWriter out = resp.getWriter();
            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
            out.print(g.toJson(response));
            out.flush();
        }else{// access out of time

            GetRestaurantResponseOutOfTime  response = new GetRestaurantResponseOutOfTime();
            response.setResult(new Result("success","You have requested the restaurants out of opening time, retry between 10 a.m. and 23.59 p.m."));

            PrintWriter out = resp.getWriter();
            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
            out.print(g.toJson(response));
            out.flush();
        }

    }

    private Result fillResult(boolean areFound){
        Result result = new Result();
        if (areFound){
            result.setStatus("success");
            result.setMessage("");
            return result;
        }
        result.setStatus("failure");
        result.setMessage("No restaurants available in selected city");
        return result;
    }

}