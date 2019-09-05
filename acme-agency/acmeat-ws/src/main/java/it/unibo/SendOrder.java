package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.*;
import it.unibo.models.responses.Response;
import it.unibo.models.SendOrderContent;
import it.unibo.models.factory.ResponseFactory;
import it.unibo.utils.AcmeMessages;
import it.unibo.utils.ProcessEngineWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.engine.ProcessEngine;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static it.unibo.models.Status.AVAILABLE;
import static it.unibo.utils.AcmeMessages.SEND_ORDER;
import static it.unibo.utils.AcmeVariables.*;
import static it.unibo.utils.Services.BANK_REST_SERVICE_URL;


@WebServlet("/send-order")
public class SendOrder extends HttpServlet {

    @Inject
    private ProcessEngine processEngine;

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private Gson g = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ResponseFactory responseFactory = new ResponseFactory();
        ProcessEngineWrapper process = new ProcessEngineWrapper(processEngine);
        HttpSession session = req.getSession(false);
        String camundaProcessId = session!=null?(String) session.getAttribute(PROCESS_ID):"";

        if(process.isActive(camundaProcessId))
            process.setVariable(
                    camundaProcessId,
                    RESTAURANT_ORDER,
                    g.fromJson(req.getReader(), RestaurantOrder.class));

        process.correlate(camundaProcessId, SEND_ORDER);

        DeliveryOrder deliveryOrder =
                (DeliveryOrder) process.getVariable(camundaProcessId, DELIVERY_ORDER);

        RestaurantOrder restaurantOrder =
                (RestaurantOrder) process.getVariable(camundaProcessId, RESTAURANT_ORDER);

        Response response;
        if (session == null || session.getAttribute(PROCESS_ID) == null
            ||(!process.isCorrelationSuccessful()
                && session.getAttribute(SEND_ORDER)==null)) {
            response = responseFactory.getFailureResponse("No active session found");
        } else if(deliveryOrder==null || deliveryOrder.getPrice()==null) {
            response = responseFactory.getFailureResponse("No delivery companies available");
            session.setAttribute(SEND_ORDER, AcmeMessages.SEND_ORDER);
        } else if(restaurantOrder==null || restaurantOrder.status!=AVAILABLE){
            response = responseFactory.getFailureResponse("Restaurant temporally unavailable");
            session.setAttribute(SEND_ORDER, SEND_ORDER);
        } else {
            SendOrderContent content = new SendOrderContent(BANK_REST_SERVICE_URL,
                    Double.toString(deliveryOrder.getPrice() + restaurantOrder.calculateTotalPrice()));
            session.setAttribute(SEND_ORDER, SEND_ORDER);
            response = responseFactory.getSuccessResponse(content);
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(response));
        out.flush();
    }

}
