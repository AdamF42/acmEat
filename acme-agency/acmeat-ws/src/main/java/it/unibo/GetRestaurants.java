package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.responses.GetRestaurantResponse;
import it.unibo.models.entities.Restaurant;
import it.unibo.models.Result;
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

        HttpSession session = req.getSession(true);

        if (!session.isNew()) {
            LOGGER.warn("Client with active session requested new process");
            session.invalidate();
            session = req.getSession(true);
        }

        Map<String, Object> cityVariable = new HashMap<>();
        String cityParam = "city";
        cityVariable.put(cityParam,req.getParameter(cityParam));

        String processInstanceId = service
                .startProcessInstanceByMessage(START_MESSAGE, cityVariable)
                .getProcessInstanceId();
        session.setAttribute(PROCESS_ID, processInstanceId);
        LOGGER.info("Started process instance with id: {}",processInstanceId);

        Gson g = new Gson();
        GetRestaurantResponse response = new GetRestaurantResponse();
        response.setRestaurants(
                g.fromJson((String) service.getVariables(processInstanceId).get("restaurants"), ArrayList.class));
        boolean areRestaurantsAvailable = restaurantsAvailable(response.getRestaurants());
        response.setResult(fillResult(areRestaurantsAvailable));
        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(response));
        out.flush();
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

    private boolean restaurantsAvailable(List<Restaurant> restaurants){
        LOGGER.debug("Found "+restaurants.size()+" restaurants");
        return restaurants.size()>0;
    }
}