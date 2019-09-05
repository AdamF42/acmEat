package it.unibo;

import camundajar.com.google.gson.Gson;
import camundajar.com.google.gson.GsonBuilder;
import it.unibo.models.RestaurantList;
import it.unibo.models.factory.ResponseFactory;
import it.unibo.models.responses.Response;
import it.unibo.utils.ProcessEngineWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.engine.ProcessEngine;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static it.unibo.utils.AcmeMessages.GET_RESTAURANT;
import static it.unibo.utils.AcmeVariables.*;



@WebServlet("/get-restaurant")
public class GetRestaurants extends HttpServlet {

    @Inject
    ProcessEngine processEngine;

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);

        if (!session.isNew()) {
            LOGGER.warn("Client with active session requested new process");
            session.invalidate();
            session = req.getSession(true);
        }

        ProcessEngineWrapper process = new ProcessEngineWrapper(processEngine);
        Map<String, Object> cityVariable = new HashMap<>();
        String cityParam = "city";
        cityVariable.put(cityParam,req.getParameter(cityParam));

        String processInstanceId = process
                .startProcessInstanceByMessage(GET_RESTAURANT,cityVariable)
                .getProcessInstanceId();

        //check if the request is out of time
        String outOfTimeVar = (String) process.getVariable(processInstanceId,OUT_OF_TIME);

        Gson g = new GsonBuilder()
                .serializeNulls()
                .create();

        session.setAttribute(PROCESS_ID, processInstanceId);
        LOGGER.info("Started process instance with id: {}",processInstanceId);

        Response response;
        ResponseFactory responseFactory = new ResponseFactory();
        RestaurantList restaurants = g.fromJson((String) process.getVariable(processInstanceId,RESTAURANTS),RestaurantList.class);

        if (outOfTimeVar != null){
            response = responseFactory.getFailureResponse("No restaurant available. Retry between 10 a.m. and 23.59 p.m.");
        } else if (restaurants == null  || restaurants.isEmpty()){
            response = responseFactory.getFailureResponse("No restaurants available in selected city");
        } else {
            response = responseFactory.getSuccessResponse(restaurants);
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(response));
        out.flush();

    }

}