package it.unibo.models.responses;

import com.google.gson.annotations.Expose;
import it.unibo.models.Result;


public class SendOrderResponse {

    @Expose
    private String bank_url;

    @Expose
    private String total_price;

    @Expose
    private Result result;

    public void setBankUrl(String bank_url) {
        this.bank_url = bank_url;
    }

    public void setTotalPrice(String total_price) {
        this.total_price = total_price;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
