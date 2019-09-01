package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.*;
import it.unibo.models.responses.SendOrderResponse;
import it.unibo.utils.AcmeMessages;
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

import static it.unibo.SessionVariables.STATUS;
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

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(PROCESS_ID) == null) {
            sendFailureResponse(resp, "No active session found");
            return;
        }

        String camundaProcessId = (String) session.getAttribute(PROCESS_ID);
        MyProcessInstance process = new MyProcessInstance(processEngine);
        if(process.isActive(camundaProcessId))
            process.setVariable(
                camundaProcessId,
                RESTAURANT_ORDER,
                g.fromJson(req.getReader(), RestaurantOrder.class));

        if(!process.correlate(camundaProcessId, AcmeMessages.SEND_ORDER).isCorrelationSuccessful() && session.getAttribute(AcmeMessages.SEND_ORDER)==null){
            sendFailureResponse(resp, "No active session found");
            return;
        }

        session.setAttribute(AcmeMessages.SEND_ORDER, AcmeMessages.SEND_ORDER);

        //check delivery order status
        DeliveryOrder deliveryOrder =
                (DeliveryOrder) process.getVariable(camundaProcessId, DELIVERY_ORDER);

        if (deliveryOrder==null || deliveryOrder.getPrice()==null){
            sendFailureResponse(resp, "No delivery companies available");
            return;
        }

        //check restaurant order status
        RestaurantOrder restaurantOrder =
                (RestaurantOrder) process.getVariable(camundaProcessId, RESTAURANT_ORDER);

        if (restaurantOrder==null || restaurantOrder.status!=AVAILABLE){
            sendFailureResponse(resp, "Restaurant temporally unavailable");
            return;
        }

        SendOrderResponse orderResponse = new SendOrderResponse();
        orderResponse.setBankUrl(BANK_REST_SERVICE_URL);
        orderResponse.setTotalPrice(Double.toString(
                deliveryOrder.getPrice() + restaurantOrder.getTotalPrice()));
        Result result = new Result();
        result.setStatus("success");
        result.setMessage("");
        orderResponse.setResult(result);


        session.setAttribute(STATUS,SEND_ORDER);
        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(orderResponse));
        out.flush();

    }

    // TODO: move in other place
    private static void sendFailureResponse(HttpServletResponse resp, String message) throws IOException {
        Gson g = new Gson();
        SendOrderResponse orderResponse = new SendOrderResponse();
        Result result = new Result();
        result.setStatus("failure");
        result.setMessage(message);
        orderResponse.setResult(result);

        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(orderResponse));
        out.flush();
    }

}
