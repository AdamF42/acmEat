package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class RequestSendOrder {
    public String session_id;
    public String restaurant;
    public List<String> dishes;
    public String delivery_time;
    public String from;
    public String to;
}
