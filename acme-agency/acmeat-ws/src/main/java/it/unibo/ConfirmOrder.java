package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.responses.Response;
import it.unibo.models.factory.ResponseFactory;
import it.unibo.utils.ProcessEngineWrapper;
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

        ResponseFactory responseFactory = new ResponseFactory();
        ProcessEngineWrapper process = new ProcessEngineWrapper(processEngine);
        HttpSession session = req.getSession(false);
        String camundaProcessId = session!=null?(String) session.getAttribute(PROCESS_ID):"";

        if(process.isActive(camundaProcessId))
            process.setVariable(camundaProcessId, USER_TOKEN, req.getParameter("token"));

        process.correlate(camundaProcessId, CONFIRM_ORDER);
        Boolean isValidToken = (Boolean) process.getVariable(camundaProcessId, IS_VALID_TOKEN);

        Response response;
        if (session == null || camundaProcessId == null
            || !process.isCorrelationSuccessful() && session.getAttribute(CONFIRM_ORDER)==null ){
            LOGGER.warn("No active session found");
            response = responseFactory.getFailureResponse("No active session found");
        } else if (isValidToken!=null && !isValidToken) {
            LOGGER.warn("Invalid bank token");
            response = responseFactory.getFailureResponse("Invalid bank token");
            session.setAttribute(CONFIRM_ORDER, CONFIRM_ORDER);
        } else {
            response = responseFactory.getSuccessResponse();
            session.setAttribute(CONFIRM_ORDER, CONFIRM_ORDER);
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(response));
        out.flush();

    }
}
