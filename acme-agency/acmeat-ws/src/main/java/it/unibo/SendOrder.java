package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.factory.ResponseFactory;
import it.unibo.models.DeliveryOrder;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.SendOrderContent;
import it.unibo.models.responses.Response;
import it.unibo.utils.AcmeMessages;
import it.unibo.utils.ApiHttpServlet;
import it.unibo.utils.ProcessEngineAdapter;
import org.camunda.bpm.engine.ProcessEngine;

import javax.inject.Inject;
import javax.servlet.ServletException;
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
public class SendOrder extends ApiHttpServlet {

    @Inject
    private ProcessEngine processEngine;
    private final ResponseFactory responseFactory = new ResponseFactory();
    private Gson g = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProcessEngineAdapter process = new ProcessEngineAdapter(processEngine);
        HttpSession session = req.getSession(false);
        String camundaProcessId = session != null ? (String) session.getAttribute(PROCESS_ID) : "";

        if (process.isActive(camundaProcessId))
            process.setVariable(
                    camundaProcessId,
                    RESTAURANT_ORDER,
                    g.fromJson(req.getReader(), RestaurantOrder.class));

        process.correlate(camundaProcessId, SEND_ORDER);

        DeliveryOrder deliveryOrder =
                (DeliveryOrder) process.getVariable(camundaProcessId, DELIVERY_ORDER);

        RestaurantOrder restaurantOrder =
                (RestaurantOrder) process.getVariable(camundaProcessId, RESTAURANT_ORDER);

        Response response = getResponse(session, process.isCorrelationSuccessful(), deliveryOrder, restaurantOrder);
        sendResponse(resp, g.toJson(response));
    }

    private Response getResponse(HttpSession session, Boolean isCorrelationSuccessful, DeliveryOrder deliveryOrder, RestaurantOrder restaurantOrder) {
        Response response;
        if (session == null || session.getAttribute(PROCESS_ID) == null
                || (!isCorrelationSuccessful
                && session.getAttribute(SEND_ORDER) == null)) {
            response = responseFactory.createFailureResponse("No active session found");
        } else if (deliveryOrder == null || deliveryOrder.getPrice() == null) {
            response = responseFactory.createFailureResponse("No delivery companies available");
            session.setAttribute(SEND_ORDER, AcmeMessages.SEND_ORDER);
        } else if (restaurantOrder == null || restaurantOrder.status != AVAILABLE) {
            response = responseFactory.createFailureResponse("Restaurant temporally unavailable");
            session.setAttribute(SEND_ORDER, SEND_ORDER);
        } else {
            SendOrderContent content = new SendOrderContent(BANK_REST_SERVICE_URL,
                    Double.toString(deliveryOrder.getPrice() + restaurantOrder.calculateTotalPrice()));
            session.setAttribute(SEND_ORDER, SEND_ORDER);
            response = responseFactory.createSuccessResponse(content);
        }
        return response;
    }
}
