package it.unibo.models.responses;

import it.unibo.models.Result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SendOrderResponse {
    public String bank_url;
    public String total_price;
    public Result result;
}
