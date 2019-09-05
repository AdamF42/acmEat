package it.unibo;

import com.google.gson.Gson;
import it.unibo.models.responses.Response;
import it.unibo.models.factory.ResponseFactory;
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

        ResponseFactory responseFactory = new ResponseFactory();
        ProcessEngineWrapper process = new ProcessEngineWrapper(processEngine);
        HttpSession session = req.getSession(false);
        String camundaProcessId = session!=null?(String) session.getAttribute(PROCESS_ID):"";

        Response response;
        if (session == null || session.getAttribute(PROCESS_ID) == null ||
                (!process.correlate(camundaProcessId,ABORT_ORDER).isCorrelationSuccessful() && session.getAttribute(ABORT_ORDER)==null)) {
            response = responseFactory.getFailureResponse("No active session found");
        } else {
            session.setAttribute(ABORT_ORDER,ABORT_ORDER);
            response = responseFactory.getSuccessResponse();
        }

        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(response));
        out.flush();


    }

}
