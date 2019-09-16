package it.unibo;

import it.unibo.models.DeliveryOrder;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.SendOrderContent;
import it.unibo.models.responses.Response;
import it.unibo.utils.AcmeatHttpServlet;
import it.unibo.utils.ProcessEngineAdapter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static it.unibo.models.Status.AVAILABLE;
import static it.unibo.utils.AcmeMessages.SEND_ORDER;
import static it.unibo.utils.AcmeVariables.*;
import static it.unibo.utils.Services.BANK_REST_SERVICE_URL;


@WebServlet("/send-order")
public class SendOrder extends AcmeatHttpServlet {

    private HttpSession session;
    private ProcessEngineAdapter process;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        process = new ProcessEngineAdapter(processEngine);
        session = req.getSession(false);
        String camundaProcessId = session != null ? (String) session.getAttribute(PROCESS_ID) : "";

        if (process.isActive(camundaProcessId) && session != null && session.getAttribute(SEND_ORDER) == null)
            process.setVariable(
                    camundaProcessId,
                    RESTAURANT_ORDER,
                    commonModules.getGson().fromJson(req.getReader(), RestaurantOrder.class));
        process.correlate(camundaProcessId, SEND_ORDER);

        DeliveryOrder deliveryOrder =
                (DeliveryOrder) process.getVariable(camundaProcessId, DELIVERY_ORDER);
        RestaurantOrder restaurantOrder =
                (RestaurantOrder) process.getVariable(camundaProcessId, RESTAURANT_ORDER);

        Response response = getResponse(deliveryOrder, restaurantOrder);

        sendResponse(resp, commonModules.getGson().toJson(response));
    }

    private Response getResponse(DeliveryOrder deliveryOrder, RestaurantOrder restaurantOrder) {
        if (session == null || session.getAttribute(PROCESS_ID) == null
                || (!process.isCorrelationSuccessful()
                && session.getAttribute(SEND_ORDER) == null)) {
            return responseFactory.createFailureResponse("No active session found");
        }
        session.setAttribute(SEND_ORDER, SEND_ORDER);
        if (deliveryOrder == null || deliveryOrder.getPrice() == null) {
            return responseFactory.createFailureResponse("No delivery companies available");
        } else if (restaurantOrder == null || restaurantOrder.status != AVAILABLE) {
            return responseFactory.createFailureResponse("Restaurant temporarily unavailable");
        } else {
            SendOrderContent content = new SendOrderContent(BANK_REST_SERVICE_URL,
                    Double.toString(deliveryOrder.getPrice() + restaurantOrder.calculateTotalPrice()));
            return responseFactory.createSuccessResponse(content);
        }
    }
}
