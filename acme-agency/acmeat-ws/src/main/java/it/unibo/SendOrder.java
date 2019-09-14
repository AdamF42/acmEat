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

import static it.unibo.utils.AcmeMessages.SEND_ORDER;
import static it.unibo.utils.AcmeVariables.*;


@WebServlet("/send-order")
public class SendOrder extends ApiHttpServlet {

    @Inject
    private ProcessEngine processEngine;
    private final ResponseService responseService = new ResponseService();
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

        Response response = responseService.getResponse(session, process.isCorrelationSuccessful(), deliveryOrder, restaurantOrder);
        sendResponse(resp, g.toJson(response));
    }
}
