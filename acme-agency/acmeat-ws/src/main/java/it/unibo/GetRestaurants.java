package it.unibo;

import it.unibo.models.RestaurantList;
import it.unibo.models.responses.Response;
import it.unibo.utils.AcmeatWsHttpServlet;
import it.unibo.utils.ProcessEngineAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static it.unibo.utils.AcmeMessages.GET_RESTAURANT;
import static it.unibo.utils.AcmeVariables.*;


@WebServlet("/get-restaurants")
public class GetRestaurants extends AcmeatWsHttpServlet {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession(true);
        ProcessEngineAdapter process = new ProcessEngineAdapter(processEngine);
        Map<String, Object> cityVariable = new HashMap<>();

        if (!session.isNew()) {
            LOGGER.warn("Client with active session requested new process");
            session.invalidate();
            session = req.getSession(true);
        }

        cityVariable.put("city", req.getParameter("city"));

        String processInstanceId = process
                .startProcessInstanceByMessage(GET_RESTAURANT, cityVariable)
                .getProcessInstanceId();

        String outOfTimeVar = (String) process.getVariable(processInstanceId, IN_TIME);

        session.setAttribute(PROCESS_ID, processInstanceId);
        LOGGER.info("Started process instance with id: {}", processInstanceId);

        RestaurantList restaurants = gsonFactory.getGson().fromJson((String) process.getVariable(processInstanceId, RESTAURANTS), RestaurantList.class);

        Response response = getResponse(outOfTimeVar, restaurants);
        sendResponse(resp, gsonFactory.getGson().toJson(response));
    }

    private Response getResponse(String inTimeVar, RestaurantList restaurants) {
        Response response;
        if (inTimeVar == null) {
            response = responseFactory.createFailureResponse("No restaurant available. Retry between 10 a.m. and 20 p.m.");
        } else if (restaurants == null || restaurants.isEmpty()) {
            response = responseFactory.createFailureResponse("No restaurants available in selected city");
        } else {
            response = responseFactory.createSuccessResponse(restaurants);
        }
        return response;
    }
}