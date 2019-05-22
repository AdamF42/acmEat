package it.unibo.soseng;

import org.codehaus.jackson.annotate.JsonProperty;

public class Company {

    @JsonProperty
    private boolean availability;

    @JsonProperty
    private int id;

    @JsonProperty
    private String name;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Id : " + this.id + '\n');
        stringBuilder.append("Name : " + this.name + '\n');
        stringBuilder.append("Availability : " + this.availability + '\n');
        return stringBuilder.toString();
    }
}
