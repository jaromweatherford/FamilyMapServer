package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

import RequestObjects.FillRequest;
import ResponseObjects.MessageResponse;
import Service.AuthorizationService;
import Service.FillService;
import Service.InternalServerErrorException;
import Service.InvalidInputException;
import Service.UserNotFoundException;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 * Created by jaromwea on 10/26/18.
 */

public class FillHandler implements HttpHandler {
    private static Logger logger;

    static {
        logger = Logger.getLogger("FamilyMapServer");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.entering("FillHandler", "handle");

        boolean success = false;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                String path = exchange.getRequestURI().getPath();
                int lastSlash = path.lastIndexOf('/');
                int secondLastSlash = path.substring(0, lastSlash).lastIndexOf('/');
                int generations = Integer.parseInt(path.substring(lastSlash + 1));
                String userName = path.substring(secondLastSlash + 1, lastSlash);
                FillRequest fillRequest = new FillRequest(userName, generations);
                FillService fillService = new FillService();
                MessageResponse messageResponse = fillService.run(fillRequest);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                String jsonResponse = gson.toJson(messageResponse);
                OutputStream respBody = exchange.getResponseBody();
                OutputStreamWriter writer = new OutputStreamWriter(respBody);
                writer.write(jsonResponse);
                writer.flush();
                respBody.close();
                success = true;
                System.out.println("Successfully filled");
            }
            if (!success) {
                logger.fine("Failed to fill");
                System.out.println("Failed to fill");
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
            logger.severe("IOException in FillHandler.handle: " + e.getMessage());
            System.out.println("IOException in FillHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
        catch (InternalServerErrorException e) {
            logger.severe("InternalServerErrorException in FillHandler.handle: " + e.getMessage());
            System.out.println("InternalServerErrorException in FillHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
        catch (UserNotFoundException e) {
            logger.severe("UserNotFoundException in FillHandler.handle: " + e.getMessage());
            System.out.println("UserNotFoundException in FillHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(respBody);
            MessageResponse messageResponse = new MessageResponse("User not found");
            String jsonResponse = gson.toJson(messageResponse);
            writer.write(jsonResponse);
            writer.flush();
            exchange.getResponseBody().close();
        }
        catch (InvalidInputException e) {
            logger.severe("InvalidInputException in FillHandler.handle: " + e.getMessage());
            System.out.println("InvalidInputException in FillHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(respBody);
            MessageResponse messageResponse = new MessageResponse("Improper Input");
            String jsonResponse = gson.toJson(messageResponse);
            writer.write(jsonResponse);
            writer.flush();
            exchange.getResponseBody().close();
        }
        catch (Error e) {
            e.printStackTrace();
        }
        finally {
            logger.exiting("FillHandler", "handle");
        }
    }
}