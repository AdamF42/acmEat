package it.unibo.models.response;

import com.google.gson.annotations.Expose;
import it.unibo.models.Result;


public class SuccessResponse implements Response {

    @Expose
    private Result result = new Result();

    public SuccessResponse() {
        this.result.setMessage("");
        this.result.setStatus(Result.SUCCESS);
    }
}
