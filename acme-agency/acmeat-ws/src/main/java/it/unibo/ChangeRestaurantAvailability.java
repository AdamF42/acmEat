package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.factory.ResponseFactory;
import it.unibo.models.RestaurantAvailability;
import it.unibo.models.responses.Response;
import it.unibo.utils.ApiHttpServlet;
import it.unibo.utils.ProcessEngineAdapter;
import it.unibo.utils.repo.RestaurantRepository;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;
import org.camunda.bpm.engine.ProcessEngine;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static it.unibo.utils.AcmeMessages.CHANGE_RESTAURANT_AVAILABILITY;


@WebServlet("/change-availability")
public class ChangeRestaurantAvailability extends ApiHttpServlet {

    @Inject
    private ProcessEngine processEngine;
    private final ResponseFactory responseFactory = new ResponseFactory();
    private final Gson g = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        RestaurantRepository repo = new RestaurantRepositoryImpl();
        ProcessEngineAdapter process = new ProcessEngineAdapter(processEngine);

        RestaurantAvailability availability = g.fromJson(req.getReader(), RestaurantAvailability.class);
        process.correlate(CHANGE_RESTAURANT_AVAILABILITY);

        Response response = getResponse(repo, process.isCorrelationSuccessful(), availability);
        sendResponse(resp, g.toJson(response));
    }

    private Response getResponse(RestaurantRepository repo, Boolean isCorrelationSuccessful, RestaurantAvailability availability) {
        Response response;
        if (!isCorrelationSuccessful) {
            response = responseFactory.createFailureResponse("Out of time");
        } else {
            try {
                repo.addOrUpdateOpeningTime(availability);
                response = responseFactory.createSuccessResponse();
            } catch (IOException e) {
                response = responseFactory.createFailureResponse("Unable to update db");
                e.printStackTrace();
            }
        }
        return response;
    }
}
