package it.unibo.models.responses;

import com.google.gson.annotations.Expose;
import it.unibo.models.Result;
import it.unibo.models.SendOrderContent;


public class SendOrderResponse  implements Response {

    @Expose
    private String bank_url;

    @Expose
    private String total_price;

    @Expose
    private Result result;

    public SendOrderResponse(SendOrderContent content, Result result) {
        this.bank_url = content.bank_url;
        this.total_price = content.total_price;
        this.result = result;
    }

    public SendOrderResponse() {
    }
}
