package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Result {
    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String status;
    String message;
}
