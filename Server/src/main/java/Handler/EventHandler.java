package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

import RequestObjects.EventRequest;
import ResponseObjects.EventResponse;
import ResponseObjects.MessageResponse;
import Service.AuthorizationService;
import Service.EventNotFoundException;
import Service.EventService;
import Service.InternalServerErrorException;
import sun.net.www.protocol.http.HttpURLConnection;

public class EventHandler implements HttpHandler {
    private static Logger logger;

    static {
        logger = Logger.getLogger("FamilyMapServer");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.entering("EventHandler", "handle");

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
                        EventRequest eventRequest = new EventRequest(ID);
                        EventService eventService = new EventService();
                        EventResponse eventResponse = eventService.run(eventRequest);
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        String jsonResponse = gson.toJson(eventResponse);
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
                logger.fine("Event not found");
                System.out.println("Event not found");
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
        catch (EventNotFoundException e) {
            logger.fine("Event not found");
            System.out.println("Event not found");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(respBody);
            MessageResponse messageResponse = new MessageResponse("Event not found");
            String jsonResponse = gson.toJson(messageResponse);
            writer.write(jsonResponse);
            writer.flush();
            respBody.close();
        }
        catch (InternalServerErrorException e) {
            logger.severe("Internal server error in EventHandler");
            System.out.println("Internal server error in EventHandler");
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
