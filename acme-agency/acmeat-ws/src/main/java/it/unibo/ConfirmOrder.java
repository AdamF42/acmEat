package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.Result;
import it.unibo.models.responses.ConfirmOrderResponse;
import org.camunda.bpm.engine.ProcessEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import static it.unibo.utils.AcmeMessages.CONFIRM_ORDER;
import static it.unibo.utils.AcmeVariables.*;

@WebServlet("/confirm")
public class ConfirmOrder extends HttpServlet {

    @Inject
    private ProcessEngine processEngine;

    private final Logger LOGGER = LogManager.getLogger(this.getClass());
    private Gson g = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(PROCESS_ID) == null){
            LOGGER.warn("No active session found");
            sendFailureResponse(resp,"No active session found");
            return;
        }

        String camundaProcessId = (String) session.getAttribute(PROCESS_ID);
        MyProcessInstance process = new MyProcessInstance(processEngine);

        if(process.isActive(camundaProcessId)){
            process.setVariable(camundaProcessId, USER_TOKEN, req.getParameter("token"));
        }

        process.correlate(camundaProcessId, CONFIRM_ORDER);
        Boolean isValidToken = (Boolean) process.getVariable(camundaProcessId, IS_VALID_TOKEN);

        if(!process.isCorrelationSuccessful() && session.getAttribute(CONFIRM_ORDER)==null){
                sendFailureResponse(resp, "No active process found");
                return;
        }

        session.setAttribute(CONFIRM_ORDER, CONFIRM_ORDER);

        if(isValidToken!=null && !isValidToken){
            sendFailureResponse(resp, "Invalid bank token");
            return;
        }
        // return to user confirmation
        ConfirmOrderResponse response = new ConfirmOrderResponse();
        Result result = new Result();
        result.setMessage("Confirmed");
        result.setStatus("success");
        response.setResult(result);
        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(response));
        out.flush();


    }

    private static void sendFailureResponse(HttpServletResponse resp, String message) throws IOException {
        Gson g = new Gson();
        ConfirmOrderResponse orderResponse = new ConfirmOrderResponse();
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
