package it.unibo;

import it.unibo.models.DeliveryOrder;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.responses.Response;
import it.unibo.utils.AcmeatHttpServlet;
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
public class ConfirmOrder extends AcmeatHttpServlet {

    private HttpSession session;
    private ProcessEngineAdapter process;

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        process = new ProcessEngineAdapter(processEngine);
        session = req.getSession(false);
        String camundaProcessId = session != null ? (String) session.getAttribute(PROCESS_ID) : "";

        if (process.isActive(camundaProcessId) && session != null && session.getAttribute(CONFIRM_ORDER) == null)
            process.setVariable(camundaProcessId, USER_TOKEN, req.getParameter("token"));

        process.correlate(camundaProcessId, CONFIRM_ORDER);
        Boolean isValidToken = (Boolean) process.getVariable(camundaProcessId, IS_VALID_TOKEN);
        Boolean isReachableBankService = (Boolean) process.getVariable(camundaProcessId, IS_UNREACHABLE_BANK_SERVICE);
        RestaurantOrder restaurantOrder = (RestaurantOrder) process.getVariable(camundaProcessId, RESTAURANT_ORDER);
        DeliveryOrder deliveryOrder = (DeliveryOrder) process.getVariable(camundaProcessId, DELIVERY_ORDER);

        Response response = getResponse(isValidToken, isReachableBankService, restaurantOrder, deliveryOrder);
        sendResponse(resp, commonModules.getGson().toJson(response));
    }

    private Response getResponse(Boolean isValidToken, Boolean isUnreachableBankService, RestaurantOrder restaurantOrder, DeliveryOrder deliveryOrder) {
        if (session == null || !process.isCorrelationSuccessful() && session.getAttribute(CONFIRM_ORDER) == null) {
            return responseFactory.createFailureResponse("No active session found");
        }
        session.setAttribute(CONFIRM_ORDER, CONFIRM_ORDER);
        if (isUnreachableBankService != null && isUnreachableBankService) {
            return responseFactory.createFailureResponse("Unable to verify bank token");
        } else if (isValidToken != null && !isValidToken) {
            return responseFactory.createFailureResponse("Invalid bank token");
        } else if (restaurantOrder != null && restaurantOrder.status != ACCEPTED) {
            return responseFactory.createFailureResponse("Impossible to confirm restaurant order");
        } else if (deliveryOrder != null && deliveryOrder.status != ACCEPTED) {
            return responseFactory.createFailureResponse("Impossible to confirm delivery order");
        } else {
            return responseFactory.createSuccessResponse();
        }
    }
}