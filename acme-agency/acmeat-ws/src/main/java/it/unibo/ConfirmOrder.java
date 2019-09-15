package it.unibo;

import it.unibo.models.DeliveryOrder;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.responses.Response;
import it.unibo.utils.AcmeatWsHttpServlet;
import it.unibo.utils.ProcessEngineAdapter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static it.unibo.models.Status.ACCEPTED;
import static it.unibo.utils.AcmeMessages.CONFIRM_ORDER;
import static it.unibo.utils.AcmeVariables.*;

@WebServlet("/confirm")
public class ConfirmOrder extends AcmeatWsHttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ProcessEngineAdapter process = new ProcessEngineAdapter(processEngine);
        HttpSession session = req.getSession(false);
        String camundaProcessId = session != null ? (String) session.getAttribute(PROCESS_ID) : "";

        if (process.isActive(camundaProcessId) && session != null && session.getAttribute(CONFIRM_ORDER) == null)
            process.setVariable(camundaProcessId, USER_TOKEN, req.getParameter("token"));

        process.correlate(camundaProcessId, CONFIRM_ORDER);
        Boolean isValidToken = (Boolean) process.getVariable(camundaProcessId, IS_VALID_TOKEN);
        Boolean isReachableBankService = (Boolean) process.getVariable(camundaProcessId, IS_UNREACHABLE_BANK_SERVICE);
        RestaurantOrder restaurantOrder = (RestaurantOrder) process.getVariable(camundaProcessId, RESTAURANT_ORDER);
        DeliveryOrder deliveryOrder = (DeliveryOrder) process.getVariable(camundaProcessId, DELIVERY_ORDER);

        Response response = getResponse(session, process.isCorrelationSuccessful(), isValidToken, isReachableBankService, restaurantOrder, deliveryOrder);
        sendResponse(resp, gsonFactory.getGson().toJson(response));
    }

    private Response getResponse(HttpSession session, Boolean isCorrelationSuccessful, Boolean isValidToken, Boolean isUnreachableBankService, RestaurantOrder restaurantOrder, DeliveryOrder deliveryOrder) {
        Response response;
        if (session == null || !isCorrelationSuccessful && session.getAttribute(CONFIRM_ORDER) == null) {
            response = responseFactory.createFailureResponse("No active session found");
        } else if (isUnreachableBankService != null && isUnreachableBankService) {
            response = responseFactory.createFailureResponse("Unable to verify bank token");
            session.setAttribute(CONFIRM_ORDER, CONFIRM_ORDER);
        } else if (isValidToken != null && !isValidToken) {
            response = responseFactory.createFailureResponse("Invalid bank token");
            session.setAttribute(CONFIRM_ORDER, CONFIRM_ORDER);
        } else if (restaurantOrder != null && restaurantOrder.status != ACCEPTED) {
            response = responseFactory.createFailureResponse("Impossible to confirm restaurant order");
            session.setAttribute(CONFIRM_ORDER, CONFIRM_ORDER);
        } else if (deliveryOrder != null && deliveryOrder.status != ACCEPTED) {
            response = responseFactory.createFailureResponse("Impossible to confirm delivery order");
            session.setAttribute(CONFIRM_ORDER, CONFIRM_ORDER);
        } else {
            response = responseFactory.createSuccessResponse();
            session.setAttribute(CONFIRM_ORDER, CONFIRM_ORDER);
        }
        return response;
    }
}