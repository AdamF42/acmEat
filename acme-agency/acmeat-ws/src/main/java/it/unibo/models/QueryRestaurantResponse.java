package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class QueryRestaurantResponse {
    String session_id;

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    List<Restaurant> restaurants;
    Result result;

    public void setSessionId(String session_id) {
        this.session_id = session_id;
    }
    public void setResult(Result result){this.result=result;}
}
