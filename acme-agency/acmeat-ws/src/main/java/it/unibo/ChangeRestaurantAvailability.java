package it.unibo;

import it.unibo.models.RestaurantAvailability;
import it.unibo.models.responses.Response;
import it.unibo.utils.AcmeatHttpServlet;
import it.unibo.utils.ProcessEngineAdapter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static it.unibo.utils.AcmeMessages.CHANGE_RESTAURANT_AVAILABILITY;


@WebServlet("/change-availability")
public class ChangeRestaurantAvailability extends AcmeatHttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ProcessEngineAdapter process = new ProcessEngineAdapter(processEngine);

        RestaurantAvailability availability = commonModules.getGson().fromJson(req.getReader(), RestaurantAvailability.class);
        process.correlate(CHANGE_RESTAURANT_AVAILABILITY);
        Response response = getResponse(process.isCorrelationSuccessful(), availability);

        sendResponse(resp, commonModules.getGson().toJson(response));
    }

    private Response getResponse(Boolean isCorrelationSuccessful, RestaurantAvailability availability) {
        if (!isCorrelationSuccessful) {
            return responseFactory.createFailureResponse("Out of time");
        }
        try {
            commonModules.getRestaurantRepository().addOrUpdateOpeningTime(availability);
            return responseFactory.createSuccessResponse();
        } catch (IOException e) {
            e.printStackTrace();
            return responseFactory.createFailureResponse("Unable to update db");
        }
    }
}
