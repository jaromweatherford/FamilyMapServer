package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

import RequestObjects.LoadRequest;
import ResponseObjects.MessageResponse;
import Service.AuthorizationService;
import Service.ClearService;
import Service.LoadService;
import Service.InternalServerErrorException;
import Service.InvalidInputException;
import Service.UserNotFoundException;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 * Created by jaromwea on 10/26/18.
 */

public class LoadHandler implements HttpHandler {
    private static Logger logger;

    static {
        logger = Logger.getLogger("FamilyMapServer");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.entering("LoadHandler", "handle");
        System.out.println("Entering LoadHandler.handle");

        boolean success = false;
        Gson gson = new Gson();
        try {
            System.out.println("Checking post...");
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                System.out.println("Post good");
                InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
                System.out.println("Reading json...");
                LoadRequest loadRequest = gson.fromJson(reader, LoadRequest.class);
                System.out.println("json good");
                LoadService loadService = new LoadService();
                System.out.println("Clearing in preparation...");
                new ClearService().run();
                System.out.println("Clear successful");
                MessageResponse messageResponse = loadService.run(loadRequest);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                String jsonResponse = gson.toJson(messageResponse);
                OutputStream respBody = exchange.getResponseBody();
                OutputStreamWriter writer = new OutputStreamWriter(respBody);
                writer.write(jsonResponse);
                writer.flush();
                respBody.close();
                success = true;
                System.out.println("Successfully loaded");
            }
            if (!success) {
                logger.fine("Failed to load");
                System.out.println("Failed to load");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                MessageResponse messageResponse = new MessageResponse("Improper input");
                String jsonResponse = gson.toJson(messageResponse);
                OutputStream respBody = exchange.getResponseBody();
                OutputStreamWriter writer = new OutputStreamWriter(respBody);
                writer.write(jsonResponse);
                writer.flush();
                respBody.close();
            }
        }
        catch (IOException e) {
            logger.severe("IOException in LoadHandler.handle: " + e.getMessage());
            System.out.println("IOException in LoadHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
        catch (InternalServerErrorException e) {
            logger.severe("InternalServerErrorException in LoadHandler.handle: " + e.getMessage());
            System.out.println("InternalServerErrorException in LoadHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
        catch (Exception e) {
            logger.fine("Failed to load");
            System.out.println("Failed to load");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            MessageResponse messageResponse = new MessageResponse("Improper input");
            String jsonResponse = gson.toJson(messageResponse);
            OutputStream respBody = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(respBody);
            writer.write(jsonResponse);
            writer.flush();
            respBody.close();
        }
        /*catch (InvalidInputException e) {
            logger.severe("InvalidInputException in LoadHandler.handle: " + e.getMessage());
            System.out.println("InvalidInputException in LoadHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(respBody);
            MessageResponse messageResponse = new MessageResponse("Improper Input");
            String jsonResponse = gson.toJson(messageResponse);
            writer.write(jsonResponse);
            writer.flush();
            exchange.getResponseBody().close();
        }*/
        finally {
            logger.exiting("LoadHandler", "handle");
        }
    }
}