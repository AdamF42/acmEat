package it.unibo.models.response;

import com.google.gson.annotations.Expose;
import it.unibo.models.Result;

public class SuccessResult {

    @Expose
    private static Result result = new Result();

    public SuccessResult() {
        result.setStatus(Result.SUCCESS);
        result.setMessage("");
    }

    public static Result getResult() {
        return result;
    }
}
