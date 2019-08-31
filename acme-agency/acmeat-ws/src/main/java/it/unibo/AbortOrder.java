package it.unibo;

import com.google.gson.Gson;
import it.unibo.models.Result;
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

import static it.unibo.SendOrder.sendFailureResponse;
import static it.unibo.utils.AcmeVariables.*;


@WebServlet("/abort")
public class AbortOrder extends HttpServlet {

    @Inject
    private ProcessEngine processEngine;

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private Gson g = new Gson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RuntimeService service = processEngine.getRuntimeService();
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute(AcmeMessages.GET_ORDER) == null) {
            LOGGER.warn("No active session found");
            sendFailureResponse(resp, "No active session found");
            return;
        }

        String camundaProcessId = (String) session.getAttribute(PROCESS_ID);

        if (camundaProcessId == null) {
            LOGGER.warn("No process id found");
            sendFailureResponse(resp, "No process id found");
            return;
        }

        try{
            //session.setAttribute(AcmeMessages.CONFIRM_ORDER, AcmeMessages.CONFIRM_ORDER);

            // TODO: check processId status in DB...


            service.createMessageCorrelation(AcmeMessages.ABORT_ORDER)
                    .processInstanceId(camundaProcessId)
                    .correlate();


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

        }catch (Exception e){
            LOGGER.error(e);
            sendFailureResponse(resp, e.getMessage());
        }

    }
}
