package Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

import ResponseObjects.MessageResponse;
import Service.AuthorizationService;
import Service.ClearService;
import Service.InternalServerErrorException;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 * Created by jaromwea on 10/26/18.
 */

public class ClearHandler implements HttpHandler {
    private static Logger logger;

    static {
        logger = Logger.getLogger("FamilyMapServer");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.entering("ClearHandler", "handle");

        boolean success = false;
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                ClearService clearService = new ClearService();
                MessageResponse messageResponse = clearService.run();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(messageResponse);
                OutputStream respBody = exchange.getResponseBody();
                OutputStreamWriter writer = new OutputStreamWriter(respBody);
                writer.write(jsonResponse);
                writer.flush();
                respBody.close();
                success = true;
                System.out.println("Successfully cleared");
            }
            if (!success) {
                logger.fine("Failed to clear");
                System.out.println("Failed to clear");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                MessageResponse messageResponse = new MessageResponse("Improper input");
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(messageResponse);
                OutputStream respBody = exchange.getResponseBody();
                OutputStreamWriter writer = new OutputStreamWriter(respBody);
                writer.write(jsonResponse);
                writer.flush();
                respBody.close();
            }
        }
        catch (IOException e) {
            logger.severe("IOException in ClearHandler.handle: " + e.getMessage());
            System.out.println("IOException in ClearHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
        catch (InternalServerErrorException e) {
            logger.severe("InternalServerErrorException in ClearHandler.handle: " + e.getMessage());
            System.out.println("InternalServerErrorException in ClearHandler.handle: " + e.getMessage());
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
        catch (Error e) {
            e.printStackTrace();
        }
        finally {
            logger.exiting("ClearHandler", "handle");
        }
    }
}
