package it.unibo.utils;

import it.unibo.models.*;
import it.unibo.models.factory.ResponseFactory;
import it.unibo.models.responses.Response;
import it.unibo.utils.repo.RestaurantRepository;

import javax.servlet.http.HttpSession;

import java.io.IOException;

import static it.unibo.models.Status.AVAILABLE;
import static it.unibo.utils.AcmeMessages.*;
import static it.unibo.utils.AcmeVariables.PROCESS_ID;
import static it.unibo.utils.Services.BANK_REST_SERVICE_URL;

public class ResponseService {

    private ResponseFactory responseFactory;

    public ResponseService() {
        this.responseFactory = new ResponseFactory();
    }

    public Response getResponse(String outOfTimeVar, RestaurantList restaurants) {
        Response response;
        if (outOfTimeVar != null) {
            response = responseFactory.createFailureResponse("No restaurant available. Retry between 10 a.m. and 23.59 p.m.");
        } else if (restaurants == null || restaurants.isEmpty()) {
            response = responseFactory.createFailureResponse("No restaurants available in selected city");
        } else {
            response = responseFactory.createSuccessResponse(restaurants);
        }
        return response;
    }

    public Response getResponse(ProcessEngineAdapter process, HttpSession session, DeliveryOrder deliveryOrder, RestaurantOrder restaurantOrder) {
        Response response;
        if (session == null || session.getAttribute(PROCESS_ID) == null
                || (!process.isCorrelationSuccessful()
                && session.getAttribute(SEND_ORDER) == null)) {
            response = responseFactory.createFailureResponse("No active session found");
        } else if (deliveryOrder == null || deliveryOrder.getPrice() == null) {
            response = responseFactory.createFailureResponse("No delivery companies available");
            session.setAttribute(SEND_ORDER, AcmeMessages.SEND_ORDER);
        } else if (restaurantOrder == null || restaurantOrder.status != AVAILABLE) {
            response = responseFactory.createFailureResponse("Restaurant temporally unavailable");
            session.setAttribute(SEND_ORDER, SEND_ORDER);
        } else {
            SendOrderContent content = new SendOrderContent(BANK_REST_SERVICE_URL,
                    Double.toString(deliveryOrder.getPrice() + restaurantOrder.calculateTotalPrice()));
            session.setAttribute(SEND_ORDER, SEND_ORDER);
            response = responseFactory.createSuccessResponse(content);
        }
        return response;
    }

    public Response getResponse(ProcessEngineAdapter process, HttpSession session, String camundaProcessId, Boolean isValidToken) {
        Response response;
        if (session == null || camundaProcessId == null
                || !process.isCorrelationSuccessful() && session.getAttribute(CONFIRM_ORDER) == null) {
            response = responseFactory.createFailureResponse("No active session found");
        } else if (isValidToken != null && !isValidToken) {
            response = responseFactory.createFailureResponse("Invalid bank token");
            session.setAttribute(CONFIRM_ORDER, CONFIRM_ORDER);
        } else {
            response = responseFactory.createSuccessResponse();
            session.setAttribute(CONFIRM_ORDER, CONFIRM_ORDER);
        }
        return response;
    }

    public Response getResponse(ProcessEngineAdapter process, HttpSession session, String camundaProcessId) {
        Response response;
        if (session == null || session.getAttribute(PROCESS_ID) == null ||
                (!process.correlate(camundaProcessId, ABORT_ORDER).isCorrelationSuccessful() && session.getAttribute(ABORT_ORDER) == null)) {
            response = responseFactory.createFailureResponse("No active session found");
        } else {
            session.setAttribute(ABORT_ORDER, ABORT_ORDER);
            response = responseFactory.createSuccessResponse();
        }
        return response;
    }

    public Response getResponse(ProcessEngineAdapter process, RestaurantRepository repo, RestaurantAvailability availability) {
        Response response;
        if (!process.isCorrelationSuccessful()) {
            response = responseFactory.createFailureResponse("Out of time");
        } else {
            try {
                repo.addOrUpdateOpeningTime(availability);
                response = responseFactory.createSuccessResponse();
            } catch (IOException e) {
                response = responseFactory.createFailureResponse("Unable to update db");
                e.printStackTrace();
            }
        }
        return response;
    }

    public Response getResponse(ProcessEngineAdapter process, RestaurantRepository repo, RestaurantMenu menuChange) {
        Response response;
        if (!process.isCorrelationSuccessful()) {
            response = responseFactory.createFailureResponse("Out of time");
        } else {
            try {
                repo.addOrUpdateMenu(menuChange);
                response = responseFactory.createSuccessResponse();
            } catch (IOException e) {
                response = responseFactory.createFailureResponse("Unable to update db");
                e.printStackTrace();
            }
        }
        return response;
    }
}