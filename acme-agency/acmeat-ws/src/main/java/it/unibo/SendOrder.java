package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.DeliveryCompany;
import it.unibo.models.ResponseSendOrder;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.Result;
import it.unibo.utils.AcmeMessages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.exception.NullValueException;

import javax.annotation.Resource;
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

import static it.unibo.utils.AcmeVariables.*;
import static it.unibo.utils.Services.BANK_REST_SERVICE_URL;


@WebServlet("/send-order")
public class SendOrder extends HttpServlet {

    @Resource(mappedName = "java:global/camunda-bpm-platform/process-engine/default")
    ProcessEngine processEngine;

    private final Logger LOGGER = LogManager.getLogger(this.getClass());


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson g = new Gson();
        HttpSession session = req.getSession(true);
        String camundaProcessId = (String) session.getAttribute(PROCESS_ID);
        String orderCorrelatedMessage = (String) session.getAttribute(AcmeMessages.START_MESSAGE);

        if (camundaProcessId == null ) {
            LOGGER.warn("No process associated to http session: {}", session.getId());
            Result failedOrder = new Result();
            failedOrder.setMessage("No process id found");
            failedOrder.setStatus("failed");
            PrintWriter out = resp.getWriter();
            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
            out.print(g.toJson(failedOrder));
            out.flush();
        }
        else { // return link to bank with payment informations

            RuntimeService service = processEngine.getRuntimeService();
            RestaurantOrder order = g.fromJson(req.getReader(), RestaurantOrder.class);

            // TODO: find out why you can't deserialize the object...
            String serializedOrder = g.toJson(order);

            service.setVariable(
                    camundaProcessId,
                    RESTAURANT_ORDER,
                    serializedOrder);

            if (orderCorrelatedMessage==null) {
                session.setAttribute(AcmeMessages.START_MESSAGE, AcmeMessages.START_MESSAGE);
                service.createMessageCorrelation(AcmeMessages.GET_ORDER)
                        .processInstanceId(camundaProcessId)
                        .correlate();
            }

            try{
                Double selectedCompany = (Double) service.getVariable(camundaProcessId, SELECTED_COMPANY);
                if (selectedCompany==null){
                    sendFailureResponse(resp, "failure", "No delivery companies available");
                } else{
                    ResponseSendOrder orderResponse = new ResponseSendOrder();
                    orderResponse.bank_url=BANK_REST_SERVICE_URL;
                    // TODO: calculate total . . .
                    orderResponse.total_price= Double.toString(
                            selectedCompany +
                                    Double.parseDouble(order.dishes.get(0).price));
                    orderResponse.result=new Result();
                    orderResponse.result.setStatus("success");
                    orderResponse.result.setMessage("");
                    PrintWriter out = resp.getWriter();
                    resp.setContentType(MediaType.APPLICATION_JSON);
                    resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
                    out.print(g.toJson(orderResponse));
                    out.flush();
                }
            }catch (Exception e){
                LOGGER.error(e);
                sendFailureResponse(resp,e.getMessage(),"");
            }
        }
    }

    private void sendFailureResponse(HttpServletResponse resp, String status, String message ) throws IOException {
        Gson g = new Gson();
        ResponseSendOrder orderResponse = new ResponseSendOrder();
        orderResponse.result=new Result();
        orderResponse.result.setStatus(status);
        orderResponse.result.setMessage(message);
        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(orderResponse));
        out.flush();
    }

}
