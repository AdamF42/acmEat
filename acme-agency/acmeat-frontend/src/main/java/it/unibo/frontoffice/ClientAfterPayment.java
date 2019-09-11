package it.unibo.frontoffice;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.sun.jersey.api.client.ClientResponse.Status.OK;

@WebServlet({"/client-after-payment"})
public class ClientAfterPayment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String token=req.getParameter("token");
        System.out.println("Token ricevuto dalla banca: "+token);

        //TODO:call acme-ws to verify token with /confirm?token=mytoken
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        com.sun.jersey.api.client.Client client = Client.create(clientConfig);

        System.out.println("Mando il token ad acme-ws");

        String queryURL = "http://localhost:8080/acmeat-ws/confirm?token=".concat(token);
        WebResource webResourceGet = client.resource(queryURL);
        ClientResponse response = webResourceGet
                .accept("application/json")
                .type("application/json")
                .put(ClientResponse.class);

        if (response.getStatus() == OK.getStatusCode()) {
            //response.getEntity()
            System.out.println("Risposta proveniente dalla verifica token " + response.toString());
        }
        //TODO: check if the token has been verified or not with the status field
        String status="success";

        /*if(status.equals("success")){ //pagamento con successo
            RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/success-payment.jsp");
            dispatcher.forward(req, resp);
        }else {  //pagamento con insuccesso
            RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/WEB-INF/views/failure-payment.jsp");
            dispatcher.forward(req, resp);
        }*/
    }
}

