package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

import RequestObjects.PersonsRequest;
import ResponseObjects.PersonsResponse;
import ResponseObjects.MessageResponse;
import Service.AuthorizationService;
import Service.PersonNotFoundException;
import Service.PersonsService;
import Service.InternalServerErrorException;
import sun.net.www.protocol.http.HttpURLConnection;

public class PersonsHandler implements HttpHandler {
    private static Logger logger;

    static {
        logger = Logger.getLogger("FamilyMapServer");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.entering("PersonsHandler", "handle");

        boolean success = false;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                Headers headers = exchange.getRequestHeaders();
                if (headers.containsKey("Authorization")) {
                    String authToken = headers.getFirst("Authorization");
                    if (new AuthorizationService().verifyAuthorization(authToken)) {
                        PersonsRequest personRequest = new PersonsRequest(authToken);
                        PersonsService personsService = new PersonsService();
                        PersonsResponse personResponse = personsService.run(personRequest);
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        String jsonResponse = gson.toJson(personResponse.getPeople());
                        OutputStream respBody = exchange.getResponseBody();
                        OutputStreamWriter writer = new OutputStreamWriter(respBody);
                        writer.write(jsonResponse);
                        writer.flush();
                        respBody.close();
                        success = true;
                        System.out.println("Successfully returned person");
                    }
                }
            }
            if (!success) {
                logger.fine("Person not found");
                System.out.println("Person not found");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                OutputStreamWriter writer = new OutputStreamWriter(respBody);
                MessageResponse messageResponse = new MessageResponse("Improper Input");
                String jsonResponse = gson.toJson(messageResponse);
                writer.write(jsonResponse);
                writer.flush();
                respBody.close();
            }
        }
        /*catch (PersonNotFoundException e) {
            logger.fine("Person not found");
            System.out.println("Person not found");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(respBody);
            MessageResponse messageResponse = new MessageResponse("Person not found");
            String jsonResponse = gson.toJson(messageResponse);
            writer.write(jsonResponse);
            writer.flush();
            respBody.close();
        }*/
        catch (Exception e) {
            logger.severe("Internal server error in PersonsHandler");
            System.out.println("Internal server error in PersonsHandler");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            OutputStream respBody = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(respBody);
            MessageResponse messageResponse = new MessageResponse("Internal server error");
            String jsonResponse = gson.toJson(messageResponse);
            writer.write(jsonResponse);
            writer.flush();
            respBody.close();
        }
    }

}