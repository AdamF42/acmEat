package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.QueryRestaurantResponse;
import it.unibo.models.Restaurant;
import it.unibo.models.Result;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@WebServlet("/client-get-restaurant")
public class ClientGetRestaurant extends HttpServlet {

    @Resource(mappedName = "java:global/camunda-bpm-platform/process-engine/default")
    ProcessEngine processEngine;
    
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RuntimeService service = processEngine.getRuntimeService();

        Map<String, Object> cityVariable = new HashMap<>();
        String cityParam = "city";
        cityVariable.put(cityParam,req.getParameter(cityParam));

        String startMessageId = "Message_0l6ef0l";

        String processInstanceId = service
                .startProcessInstanceByMessage(startMessageId, cityVariable)
                .getProcessInstanceId();

        LOGGER.debug("Started Process Instance with processInstanceId: "+ processInstanceId);

        String restaurants = (String) service.getVariables(processInstanceId).get("restaurants");

        LOGGER.debug("Restaurants service response: "+ restaurants);


        Gson g = new Gson();
        QueryRestaurantResponse response = g.fromJson(restaurants, QueryRestaurantResponse.class);

        boolean areRestaurantsAvailable = restaurantsAvailable(response.getRestaurants());

        response.setSessionId(fillSessionId(areRestaurantsAvailable, processInstanceId, service));

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

    private String fillSessionId(boolean areFound, String processInstanceId, RuntimeService service) {

        if (areFound) {
            return processInstanceId;
        }

        service.deleteProcessInstance(processInstanceId,"");
        LOGGER.debug("Deleted Process Instance with processInstanceId: "+ processInstanceId);
        return "";
    }

    private boolean restaurantsAvailable(List<Restaurant> restaurants){
        LOGGER.debug("Found "+restaurants.size()+" restaurants");
        return restaurants.size()>0;
    }
}