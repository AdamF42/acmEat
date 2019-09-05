package it.unibo.models;

public class SendOrderContent {
    public String bank_url;
    public String total_price;

    public SendOrderContent(String bank_url, String total_price) {
        this.bank_url = bank_url;
        this.total_price = total_price;
    }
}
