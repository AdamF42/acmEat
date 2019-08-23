package it.unibo;

import camundajar.com.google.gson.Gson;
import it.unibo.models.*;
import it.unibo.models.responses.ResponseChangingConfirmation;
import it.unibo.utils.repo.RestaurantRepository;
import it.unibo.utils.repo.impl.RestaurantRepositoryImpl;
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
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static it.unibo.utils.AcmeMessages.CHANGE_RESTAURANT_MENU;

@WebServlet("/change-rest-menu")
public class ChangeRestaurantMenu extends HttpServlet {

    @Inject
    ProcessEngine processEngine;

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RuntimeService service = processEngine.getRuntimeService();

        System.out.println("Restaurant comunicated new menu");

        Gson g = new Gson();

        RestaurantMenu menuChange = g.fromJson(req.getReader(), RestaurantMenu.class);
        String restaurantName= menuChange.restaurant;

        RestaurantRepository repo = new RestaurantRepositoryImpl();
        repo.setMenu(menuChange);

        service.createMessageCorrelation(CHANGE_RESTAURANT_MENU)
                .correlate();

        // return confirmation
        ResponseChangingConfirmation response = new ResponseChangingConfirmation();
        response.setResult(fillResult());
        PrintWriter out = resp.getWriter();
        resp.setContentType(MediaType.APPLICATION_JSON);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        out.print(g.toJson(response));
        out.flush();
    }

    private Result fillResult(){
        Result result = new Result();
        result.setStatus("success");
        result.setMessage("Your request of menu change has been received");
        return result;
    }


}

