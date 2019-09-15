package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.factory.ResponseFactory;
import it.unibo.models.RestaurantMenu;
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

import static it.unibo.utils.AcmeMessages.CHANGE_RESTAURANT_MENU;

@WebServlet("/change-menu")
public class ChangeRestaurantMenu extends ApiHttpServlet {

    @Inject
    ProcessEngine processEngine;
    private final ResponseFactory responseFactory = new ResponseFactory();
    private final Gson g = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        RestaurantRepository repo = new RestaurantRepositoryImpl();
        ProcessEngineAdapter process = new ProcessEngineAdapter(processEngine);

        RestaurantMenu menuChange = g.fromJson(req.getReader(), RestaurantMenu.class);
        process.correlate(CHANGE_RESTAURANT_MENU);
        Response response = getResponse(repo, process.isCorrelationSuccessful(), menuChange);
        sendResponse(resp, g.toJson(response));
    }

    private Response getResponse(RestaurantRepository repo, Boolean isCorrelationSuccessful, RestaurantMenu menuChange) {
        Response response;
        if (!isCorrelationSuccessful) {
            response = responseFactory.createFailureResponse("Out of time");
        } else {
            try {
                repo.addOrUpdateMenu(menuChange);
                response = responseFactory.createSuccessResponse();
            } catch (IOException e) {
                response = responseFactory.createFailureResponse("Unable to update db");
                e.printStackTrace();
            }
        }
        return response;
    }
}

