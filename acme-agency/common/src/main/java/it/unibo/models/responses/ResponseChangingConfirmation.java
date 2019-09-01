package it.unibo.models.responses;

import it.unibo.models.Result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseChangingConfirmation {

    private Result result;

    public void setResult(Result result){this.result=result;}

}
