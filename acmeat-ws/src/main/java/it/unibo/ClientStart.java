package it.unibo;


import org.camunda.bpm.engine.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/client-start")
public class ClientStart extends HttpServlet {

    @Resource(mappedName = "java:global/camunda-bpm-platform/process-engine/default")
    ProcessEngine processEngine;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String startMessageId = "Message_0l6ef0l";

//        processEngine.getRuntimeService().startProcessInstanceById("b2962d80-b14f-11e9-b0ec-f83441dca3c0");

        processEngine.getRuntimeService().startProcessInstanceByMessage(startMessageId);

//        processEngine.getRuntimeService().startProcessInstanceByMessage(startMessageId);

        resp.getOutputStream().println("Client started!");
    }
}