package it.unibo.models.response;

import com.google.gson.annotations.Expose;
import it.unibo.models.Result;
import it.unibo.models.Status;


public class ConfirmOrderResponse implements Response {

    @Expose
    private Result result;


}
