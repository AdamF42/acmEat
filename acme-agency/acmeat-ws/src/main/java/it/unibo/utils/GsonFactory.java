package it.unibo.utils;

import camundajar.com.google.gson.Gson;
import camundajar.com.google.gson.GsonBuilder;

import javax.ws.rs.Produces;

public class GsonFactory {

    @Produces
    public Gson getGson() {
        return new GsonBuilder().serializeNulls().create();
    }

}
