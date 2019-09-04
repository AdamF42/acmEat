package it.unibo.models.response;

import com.google.gson.annotations.Expose;
import it.unibo.models.Result;


public class FailureResponse implements Response {

    @Expose
    private Result result = new Result();

    public FailureResponse(String message) {
        this.result.setMessage(message);
        this.result.setStatus("Failure");
    }
}
