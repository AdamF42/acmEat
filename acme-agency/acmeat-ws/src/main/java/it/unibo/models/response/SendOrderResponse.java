package it.unibo.models.response;

import com.google.gson.annotations.Expose;
import it.unibo.models.Result;


public class SendOrderResponse  implements Response {

    @Expose
    private String bank_url;

    @Expose
    private String total_price;

    @Expose
    private Result result = SuccessResult.getResult();

    public SendOrderResponse(SendOrderContent content) {
        this.bank_url = content.bank_url;
        this.total_price = content.total_price;
    }

}
