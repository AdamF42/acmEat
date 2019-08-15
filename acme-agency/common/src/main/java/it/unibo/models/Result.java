package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;

public class Result {
    //TODO: write an enum for status
    String status;
    String message;

    public void setStatus(String status) {
        this.status = status;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
