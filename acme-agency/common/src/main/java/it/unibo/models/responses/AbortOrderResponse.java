package it.unibo.models.responses;

import com.google.gson.annotations.Expose;
import it.unibo.models.Result;


public class AbortOrderResponse {

    @Expose
    private Result result;


    public void setResult(Result result) {
        this.result = result;
    }

}
