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
import RequestObjects.LoginRequest;
import ResponseObjects.MessageResponse;
import ResponseObjects.LoginResponse;
import Service.AuthorizationService;
import Service.InvalidInputException;
import Service.LoginService;
import Service.InternalServerErrorException;
import Service.UserNameUnavailableException;
import Service.UserNotFoundException;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 * Created by jaromwea on 10/26/18.
 */

public class LoginHandler implements HttpHandler {
    private static Logger logger;

    static {
        logger = Logger.getLogger("FamilyMapServer");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.entering("LoginHandler", "handle");

        boolean success = false;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                LoginRequest loginRequest = gson.fromJson(isr, LoginRequest.class);
                LoginService loginService = new LoginService();
                LoginResponse loginResponse = loginService.run(loginRequest);
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                String jsonResponse = gson.toJson(loginResponse);
                OutputStream respBody = exchange.getResponseBody();
                OutputStreamWriter writer = new OutputStreamWriter(respBody);
                writer.write(jsonResponse);
                writer.flush();
                System.out.println(jsonResponse);
                respBody.close();
                success = true;
                System.out.println("Successfully logged in");
            }
            if (!success) {
                logger.fine("Failed to login");
                System.out.println("Failed to login");
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
            logger.severe("IOException in LoginHandler.handle: " + e.getMessage());
            System.out.println("IOException in LoginHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
        catch (InternalServerErrorException e) {
            logger.severe("InternalServerErrorException in LoginHandler.handle: " + e.getMessage());
            System.out.println("InternalServerErrorException in LoginHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
        catch (UserNotFoundException e) {
            System.out.println("Username was not found");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            MessageResponse message = new MessageResponse("User not found");
            String jsonResponse = gson.toJson(message);
            OutputStream respBody = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(respBody);
            writer.write(jsonResponse);
            writer.flush();
            respBody.close();
        }
        catch (InvalidInputException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            MessageResponse message = new MessageResponse("Wrong password");
            String jsonResponse = gson.toJson(message);
            OutputStream respBody = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(respBody);
            writer.write(jsonResponse);
            writer.flush();
            respBody.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            MessageResponse message = new MessageResponse("User not found");
            String jsonResponse = gson.toJson(message);
            OutputStream respBody = exchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(respBody);
            writer.write(jsonResponse);
            writer.flush();
            respBody.close();
        }
        finally {
            System.out.println("Exiting LoginHandler.handle");
            logger.exiting("LoginHandler", "handle");
        }
    }
}
