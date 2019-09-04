package it.unibo.models.response.factory;

import it.unibo.models.RestaurantList;
import it.unibo.models.response.*;

public class ResponseFactory {

    public Response getSuccessResponse() {
        return new SuccessResponse();
    }

    public Response getSuccessResponse(RestaurantList list) {
        return new GetRestaurantResponse(list);
    }

    public Response getSuccessResponse(SendOrderContent content) {
        return new SendOrderResponse(content);
    }

    public Response getFailureResponse(String msg) {
        return new FailureResponse(msg);
    }

}
