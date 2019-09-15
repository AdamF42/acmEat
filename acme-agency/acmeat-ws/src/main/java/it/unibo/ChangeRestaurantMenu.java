package it.unibo;

import it.unibo.models.RestaurantMenu;
import it.unibo.models.responses.Response;
import it.unibo.utils.AcmeatHttpServlet;
import it.unibo.utils.ProcessEngineAdapter;
import it.unibo.utils.repo.RestaurantRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static it.unibo.utils.AcmeMessages.CHANGE_RESTAURANT_MENU;

@WebServlet("/change-menu")
public class ChangeRestaurantMenu extends AcmeatHttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ProcessEngineAdapter process = new ProcessEngineAdapter(processEngine);

        RestaurantMenu menuChange = commonModules.getGson().fromJson(req.getReader(), RestaurantMenu.class);
        process.correlate(CHANGE_RESTAURANT_MENU);
        Response response = getResponse(commonModules.getRestaurantRepository(),
                process.isCorrelationSuccessful(), menuChange);
        sendResponse(resp, commonModules.getGson().toJson(response));
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

