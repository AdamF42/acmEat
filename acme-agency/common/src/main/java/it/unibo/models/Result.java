package it.unibo.models;


import com.google.gson.annotations.Expose;

public class Result {
    //TODO: write an enum for status
    @Expose
    private String status;

    @Expose
    private String message;

    public void setStatus(String status) {
        this.status = status;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
