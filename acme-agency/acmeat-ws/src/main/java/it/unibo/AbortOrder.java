package it.unibo;

import com.google.gson.Gson;
import it.unibo.models.Result;
import it.unibo.models.responses.AbortOrderResponse;
import it.unibo.models.responses.ConfirmOrderResponse;
import it.unibo.utils.AcmeMessages;
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

import static it.unibo.utils.AcmeMessages.ABORT_ORDER;
import static it.unibo.utils.AcmeVariables.*;


@WebServlet("/abort")
public class AbortOrder extends HttpServlet {

    @Inject
    private ProcessEngine processEngine;

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private Gson g = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(PROCESS_ID) == null) {
            sendFailureResponse(resp, "No active session found");
            return;
        }

        String camundaProcessId = (String) session.getAttribute(PROCESS_ID);
        MyProcessInstance process = new MyProcessInstance(processEngine);

        if(!process.correlate(camundaProcessId,ABORT_ORDER).isCorrelationSuccessful() && session.getAttribute(ABORT_ORDER)==null){
            sendFailureResponse(resp, "No active session found");
            return;
        }

        session.setAttribute(ABORT_ORDER,ABORT_ORDER);

        // return to user confirmation
        AbortOrderResponse response = new AbortOrderResponse();
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
        AbortOrderResponse orderResponse = new AbortOrderResponse();
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
