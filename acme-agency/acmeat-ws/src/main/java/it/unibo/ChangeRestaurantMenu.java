package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.*;
import it.unibo.models.responses.Response;
import it.unibo.models.factory.ResponseFactory;
import it.unibo.utils.repo.RestaurantRepository;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;
import org.camunda.bpm.engine.ProcessEngine;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static it.unibo.utils.AcmeMessages.CHANGE_RESTAURANT_MENU;

@WebServlet("/change-rest-menu")
public class ChangeRestaurantMenu extends HttpServlet {

    @Inject
    ProcessEngine processEngine;

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson g = new Gson();
        ResponseFactory responseFactory = new ResponseFactory();
        RestaurantMenu menuChange = g.fromJson(req.getReader(), RestaurantMenu.class);

        //todo: Check if db insert fail
        RestaurantRepository repo = new RestaurantRepositoryImpl();
        repo.addOrUpdateMenu(menuChange);
        processEngine.getRuntimeService()
                .createMessageCorrelation(CHANGE_RESTAURANT_MENU)
                .correlate();

        Response response = responseFactory
                .getSuccessResponse();
        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(response));
        out.flush();

    }

}

