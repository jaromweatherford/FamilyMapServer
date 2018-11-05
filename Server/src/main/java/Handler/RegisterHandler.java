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

import DAO.DatabaseException;
import Model.User;
import RequestObjects.FillRequest;
import RequestObjects.RegisterRequest;
import ResponseObjects.MessageResponse;
import ResponseObjects.RegisterResponse;
import Service.AuthorizationService;
import Service.FillService;
import Service.RegisterService;
import Service.InternalServerErrorException;
import Service.UserNameUnavailableException;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 * Created by jaromwea on 10/26/18.
 */

public class RegisterHandler implements HttpHandler {
    private static Logger logger;

    static {
        logger = Logger.getLogger("FamilyMapServer");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.entering("RegisterHandler", "handle");
        System.out.println("Entering RegisterHandler.handle");

        boolean success = false;
        Gson gson = new Gson();
        try {
            System.out.println("Registering");
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                RegisterRequest registerRequest = gson.fromJson(isr, RegisterRequest.class);
                RegisterService registerService = new RegisterService();
                RegisterResponse registerResponse = registerService.run(registerRequest);
                new FillService().run(new FillRequest(registerRequest.getUserName(), 4));
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                String jsonResponse = gson.toJson(registerResponse);
                OutputStream respBody = exchange.getResponseBody();
                OutputStreamWriter writer = new OutputStreamWriter(respBody);
                writer.write(jsonResponse);
                writer.flush();
                respBody.close();
                success = true;
                System.out.println("Successfully registered");
            }
            if (!success) {
                logger.fine("Failed to register");
                System.out.println("Failed to register");
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
            logger.severe("IOException in RegisterHandler.handle: " + e.getMessage());
            System.out.println("IOException in RegisterHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
        catch (InternalServerErrorException e) {
            logger.severe("InternalServerErrorException in RegisterHandler.handle: " + e.getMessage());
            System.out.println("InternalServerErrorException in RegisterHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
        catch (UserNameUnavailableException e) {
            System.out.println("Username was unavailable");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            MessageResponse message = new MessageResponse("Username unavailable");
            String jsonResponse = gson.toJson(message);
            OutputStream respBody = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(respBody);
            writer.write(jsonResponse);
            writer.flush();
            respBody.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            MessageResponse message = new MessageResponse("Invalid input");
            String jsonResponse = gson.toJson(message);
            OutputStream respBody = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(respBody);
            writer.write(jsonResponse);
            writer.flush();
            respBody.close();
        }
        finally {
            System.out.println("Exiting RegisterHandler.handle");
            logger.exiting("RegisterHandler", "handle");
        }
    }
}
