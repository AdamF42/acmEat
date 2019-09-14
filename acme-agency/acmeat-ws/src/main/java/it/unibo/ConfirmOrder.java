package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.DeliveryOrder;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.responses.Response;
import it.unibo.utils.ApiHttpServlet;
import it.unibo.utils.ProcessEngineAdapter;
import it.unibo.utils.ResponseService;
import org.camunda.bpm.engine.ProcessEngine;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static it.unibo.utils.AcmeMessages.CONFIRM_ORDER;
import static it.unibo.utils.AcmeVariables.*;

@WebServlet("/confirm")
public class ConfirmOrder extends ApiHttpServlet {

    @Inject
    private ProcessEngine processEngine;
    private final ResponseService responseService = new ResponseService();
    private final Gson g = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ProcessEngineAdapter process = new ProcessEngineAdapter(processEngine);
        HttpSession session = req.getSession(false);
        String camundaProcessId = session != null ? (String) session.getAttribute(PROCESS_ID) : "";

        if (process.isActive(camundaProcessId))
            process.setVariable(camundaProcessId, USER_TOKEN, req.getParameter("token"));

        process.correlate(camundaProcessId, CONFIRM_ORDER);
        Boolean isValidToken = (Boolean) process.getVariable(camundaProcessId, IS_VALID_TOKEN);
        Boolean isReachableBankService = (Boolean) process.getVariable(camundaProcessId, IS_UNREACHABLE_BANK_SERVICE);
        RestaurantOrder restaurantOrder = (RestaurantOrder) process.getVariable(camundaProcessId, RESTAURANT_ORDER);
        DeliveryOrder deliveryOrder = (DeliveryOrder) process.getVariable(camundaProcessId, DELIVERY_ORDER);

        Response response = responseService.getResponse(session, process.isCorrelationSuccessful(), camundaProcessId, isValidToken, isReachableBankService, restaurantOrder, deliveryOrder);
        sendResponse(resp, g.toJson(response));
    }
}