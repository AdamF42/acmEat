package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Restaurant {
    String name;
    List<String> menu;
}
