package it.unibo.factory;

import it.unibo.models.RestaurantList;
import it.unibo.models.SendOrderContent;
import it.unibo.models.responses.Response;

public interface ResponseFactory {
    Response createSuccessResponse(RestaurantList list);

    Response createSuccessResponse(SendOrderContent content);

    Response createSuccessResponse();

    Response createFailureResponse(String msg);
}
