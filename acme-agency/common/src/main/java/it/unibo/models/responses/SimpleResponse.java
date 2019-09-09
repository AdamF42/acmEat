package it.unibo.models.responses;

import com.google.gson.annotations.Expose;
import it.unibo.models.Result;


public class SimpleResponse implements Response {

    @Expose
    public Result result;

    public SimpleResponse(Result result) {
        this.result = result;
    }

    public SimpleResponse() {
    }
}
