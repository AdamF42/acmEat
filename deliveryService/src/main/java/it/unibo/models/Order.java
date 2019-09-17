package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Order {
    public Integer id;
    public Double price;
    public String company;
    public String src_address;
    public String dest_address;
    public String delivery_time;
    public Status status;

    public void checkAndSetAcceptedStatus() {
        if (status == Status.AVAILABLE) {
            status = Status.ACCEPTED;
        } else {
            status = Status.NOT_ACCEPTED;
        }
    }
}

