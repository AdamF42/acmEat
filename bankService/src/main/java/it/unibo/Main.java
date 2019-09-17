package it.unibo;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BANK_URI = "http://0.0.0.0:8070/";

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
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BANK_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        //serve static assets
        //StaticHttpHandler staticHttpHandler = new StaticHttpHandler("src/main/webapp");
        //server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/");
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl", BANK_URI));
        System.out.println("Visit start page for client user at: "+"http://localhost:8070/bank/home");
    }
}

