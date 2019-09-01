package it.unibo.models.responses;

import it.unibo.models.Result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GetRestaurantResponseOutOfTime {

    private Result result;

    public void setResult(Result result){this.result=result;}
}
