package it.unibo;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {
    private static final String BANK_URI = "http://0.0.0.0:8070/";

    private static HttpServer startServer() {

        final ResourceConfig rc = new ResourceConfig().packages("it.unibo");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BANK_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        startServer();
    }
}

