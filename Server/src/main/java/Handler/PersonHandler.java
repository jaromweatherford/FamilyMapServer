package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

import RequestObjects.PersonRequest;
import ResponseObjects.PersonResponse;
import ResponseObjects.MessageResponse;
import Service.AuthorizationService;
import Service.PersonNotFoundException;
import Service.PersonService;
import Service.InternalServerErrorException;
import sun.net.www.protocol.http.HttpURLConnection;

public class PersonHandler implements HttpHandler {
    private static Logger logger;

    static {
        logger = Logger.getLogger("FamilyMapServer");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.entering("PersonHandler", "handle");

        boolean success = false;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                Headers headers = exchange.getRequestHeaders();
                if (headers.containsKey("Authorization")) {
                    String authToken = headers.getFirst("Authorization");
                    if (new AuthorizationService().verifyAuthorization(authToken)) {
                        String url = exchange.getRequestURI().getPath();
                        String ID = url.substring(url.lastIndexOf("/") + 1);
                        PersonRequest personRequest = new PersonRequest(ID);
                        PersonService personService = new PersonService();
                        PersonResponse personResponse = personService.run(personRequest);
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        String jsonResponse = gson.toJson(personResponse);
                        OutputStream respBody = exchange.getResponseBody();
                        OutputStreamWriter writer = new OutputStreamWriter(respBody);
                        writer.write(jsonResponse);
                        writer.flush();
                        System.out.println(jsonResponse);
                        respBody.close();
                        success = true;
                        System.out.println("Successfully logged in");

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
        catch (PersonNotFoundException e) {
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
        }
        catch (InternalServerErrorException e) {
            logger.severe("Internal server error in PersonHandler");
            System.out.println("Internal server error in PersonHandler");
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