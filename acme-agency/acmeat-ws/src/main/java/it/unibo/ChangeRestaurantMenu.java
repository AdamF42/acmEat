package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.RestaurantMenu;
import it.unibo.models.responses.Response;
import it.unibo.utils.ApiHttpServlet;
import it.unibo.utils.ProcessEngineAdapter;
import it.unibo.utils.ResponseService;
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
    private final ResponseService responseService = new ResponseService();
    private final Gson g = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        RestaurantRepository repo = new RestaurantRepositoryImpl();
        ProcessEngineAdapter process = new ProcessEngineAdapter(processEngine);

        RestaurantMenu menuChange = g.fromJson(req.getReader(), RestaurantMenu.class);
        process.correlate(CHANGE_RESTAURANT_MENU);
        Response response = responseService.getResponse(process, repo, menuChange);
        sendResponse(resp, g.toJson(response));

    }
}

