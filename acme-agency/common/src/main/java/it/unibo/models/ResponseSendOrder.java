package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseSendOrder {
    public String bank_url;
    public String total_price;
    public Result result;
}
