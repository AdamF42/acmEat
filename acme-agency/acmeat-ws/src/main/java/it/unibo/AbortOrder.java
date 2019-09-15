package it.unibo;

import com.google.gson.Gson;
import it.unibo.factory.ResponseFactory;
import it.unibo.models.responses.Response;
import it.unibo.utils.ApiHttpServlet;
import it.unibo.utils.ProcessEngineAdapter;
import org.camunda.bpm.engine.ProcessEngine;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static it.unibo.utils.AcmeMessages.ABORT_ORDER;
import static it.unibo.utils.AcmeVariables.PROCESS_ID;


@WebServlet("/abort")
public class AbortOrder extends ApiHttpServlet {

    @Inject
    private ProcessEngine processEngine;
    private final Gson g = new Gson();
    private final ResponseFactory responseFactory = new ResponseFactory();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ProcessEngineAdapter process = new ProcessEngineAdapter(processEngine);
        HttpSession session = req.getSession(false);
        String camundaProcessId = session != null ? (String) session.getAttribute(PROCESS_ID) : "";
        process.correlate(camundaProcessId, ABORT_ORDER);
        Response response = getResponse(session, process.isCorrelationSuccessful());
        sendResponse(resp, g.toJson(response));
    }

    private Response getResponse(HttpSession session, Boolean isCorrelationSuccessful) {
        Response response;
        if (session == null || session.getAttribute(PROCESS_ID) == null ||
                (!isCorrelationSuccessful && session.getAttribute(ABORT_ORDER) == null)) {
            response = responseFactory.createFailureResponse("No active session found");
        } else {
            session.setAttribute(ABORT_ORDER, ABORT_ORDER);
            response = responseFactory.createSuccessResponse();
        }
        return response;
    }
}