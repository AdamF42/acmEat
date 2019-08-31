package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.*;
import it.unibo.models.responses.SendOrderResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
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

import static it.unibo.utils.AcmeMessages.GET_ORDER;
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
        if (session == null ) {
            sendFailureResponse(resp, "No active session found");
            return;
        }

        String camundaProcessId = (String) session.getAttribute(PROCESS_ID);

        if (camundaProcessId == null ) {
            sendFailureResponse(resp, "No process associated to http session");
            return;
        }

        RuntimeService service = processEngine.getRuntimeService();
        RestaurantOrder order = g.fromJson(req.getReader(), RestaurantOrder.class);

        //TODO: check the process status in db

        try{
            service.setVariable(
                    camundaProcessId,
                    RESTAURANT_ORDER,
                    order);

            service.createMessageCorrelation(GET_ORDER)
                    .processInstanceId(camundaProcessId)
                    .correlate();

            session.setAttribute(GET_ORDER,GET_ORDER);

            DeliveryOrder deliveryOrder =
                    (DeliveryOrder) service.getVariable(camundaProcessId, DELIVERY_ORDER);

            if (deliveryOrder==null || deliveryOrder.getPrice()==null){
                sendFailureResponse(resp, "No delivery companies available");
                return;
            }

            SendOrderResponse orderResponse = new SendOrderResponse();
            orderResponse.bank_url=BANK_REST_SERVICE_URL;
            // TODO: calculate total . . .
            orderResponse.total_price= Double.toString(
                    deliveryOrder.getPrice() +
                            Double.parseDouble(order.dishes.get(0).price));
            orderResponse.result=new Result();
            orderResponse.result.setStatus("success");
            orderResponse.result.setMessage("");
            PrintWriter out = resp.getWriter();
            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
            out.print(g.toJson(orderResponse));
            out.flush();

        }catch (Exception e){
            LOGGER.error(e);
            sendFailureResponse(resp, e.getMessage());
        }
    }

    // TODO: move in other place
    public static void sendFailureResponse(HttpServletResponse resp, String message) throws IOException {
        Gson g = new Gson();
//        LOGGER.warn(message);
        SendOrderResponse orderResponse = new SendOrderResponse();
        orderResponse.result=new Result();
        orderResponse.result.setStatus("failure");
        orderResponse.result.setMessage(message);
        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(orderResponse));
        out.flush();
    }

}
