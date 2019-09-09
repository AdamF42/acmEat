package it.unibo;

import camundajar.com.google.gson.Gson;
import camundajar.com.google.gson.GsonBuilder;
import it.unibo.models.RestaurantList;
import it.unibo.models.responses.Response;
import it.unibo.utils.ApiHttpServlet;
import it.unibo.utils.ProcessEngineAdapter;
import it.unibo.utils.ResponseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.engine.ProcessEngine;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static it.unibo.utils.AcmeMessages.GET_RESTAURANT;
import static it.unibo.utils.AcmeVariables.*;


@WebServlet("/get-restaurant")
public class GetRestaurants extends ApiHttpServlet {

    @Inject
    ProcessEngine processEngine;

    private final Logger LOGGER = LogManager.getLogger(this.getClass());
    private final ResponseService responseService = new ResponseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Requested restaurants");
        HttpSession session = req.getSession(true);
        Gson g = new GsonBuilder().serializeNulls().create();
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

        RestaurantList restaurants = g.fromJson((String) process.getVariable(processInstanceId, RESTAURANTS),RestaurantList.class);

        Response response = responseService.getResponse(outOfTimeVar, restaurants);
        sendResponse(resp, g.toJson(response));
    }
}