package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Order {
    public String company;
    public String src_address;
    public String dest_address;
    public String delivery_time;
    public Integer id;
    public Status status;
    public Double price;

    public static Status checkAndSetStatus(Status status){
        if(status == Status.AVAILABLE){
            return Status.ACCEPTED;
        }else{
            return Status.NOT_ACCEPTED;
        }
    }
}

