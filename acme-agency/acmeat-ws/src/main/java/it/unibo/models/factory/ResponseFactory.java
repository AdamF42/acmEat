package it.unibo.models.factory;

import it.unibo.models.RestaurantList;
import it.unibo.models.Result;
import it.unibo.models.SendOrderContent;
import it.unibo.models.responses.GetRestaurantResponse;
import it.unibo.models.responses.Response;
import it.unibo.models.responses.SendOrderResponse;
import it.unibo.models.responses.SimpleResponse;

import static it.unibo.models.Result.SUCCESS;

public class ResponseFactory {

    private static final Result SUCCESS_RESULT = new Result(SUCCESS,"");

    public Response getSuccessResponse() {
        return new SimpleResponse(SUCCESS_RESULT);
    }

    public Response getSuccessResponse(RestaurantList list) {
        return new GetRestaurantResponse(list, SUCCESS_RESULT);
    }

    public Response getSuccessResponse(SendOrderContent content) {
        return new SendOrderResponse(content, SUCCESS_RESULT);
    }

    public Response getFailureResponse(String msg) {
        Result result = new Result();
        result.setMessage(msg);
        result.setStatus(Result.FAILURE);
        return new SimpleResponse(result);
    }
}
