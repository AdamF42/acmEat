package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.DeliveryCompany;
import it.unibo.models.ResponseSendOrder;
import it.unibo.models.RestaurantOrder;
import it.unibo.models.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;

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
import java.util.Map;

import static it.unibo.utils.AcmeMessages.GET_ORDER;
import static it.unibo.utils.AcmeVariables.*;


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
        String orderCorrelatedMessage = (String) session.getAttribute(GET_ORDER);

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
            service.setVariable(
                    camundaProcessId,
                    ORDER_PARAM,
                    order); //TODO: order should be stored in db

            if (orderCorrelatedMessage==null) {
                service.createMessageCorrelation(GET_ORDER)
                        .processInstanceId(camundaProcessId)
                        .correlate();

                session.setAttribute(GET_ORDER, GET_ORDER);
            }

            DeliveryCompany selectedCompany = (DeliveryCompany) service.getVariable(camundaProcessId, SELECTED_COMPANY);

            ResponseSendOrder orderResponse = new ResponseSendOrder();
            // TODO: insert bank url...need to implement a REST interface...
            orderResponse.bank_url="";
            // TODO: calculate total . . .
            orderResponse.total_price= Double.toString(Double.valueOf(selectedCompany.price) + Double.valueOf(order.dishes.get(0).price));
            orderResponse.result=new Result();
            orderResponse.result.setStatus("success");
            orderResponse.result.setMessage("");

            PrintWriter out = resp.getWriter();
            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

            out.print(g.toJson(orderResponse));
            out.flush();
        }
    }
}
