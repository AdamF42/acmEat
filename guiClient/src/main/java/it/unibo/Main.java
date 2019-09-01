package it.unibo;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.*;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:8060/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in it.unibo package
        final ResourceConfig rc = new ResourceConfig().packages("it.unibo");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {


        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl", "http://localhost:8060/"));
        System.out.println("Visit start page for client user at: "+"http://localhost:8060/acmeat/home");
        System.out.println("Visit start page for client restaurant at: "+"http://localhost:8060/acmeat/home-restaurant");
        System.out.println("Visit start page for client user at: "+"http://0.0.0.0:8060/acmeat/home");
        System.out.println("Visit start page for client restaurant at: "+"http://0.0.0.0:8060/acmeat/home-restaurant");
    }
}

