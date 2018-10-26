package Handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

import sun.net.www.protocol.http.HttpURLConnection;

/**
 * Based on Professor Rodham's example
 */

public class FileHandler implements HttpHandler {
    private static Logger logger;

    static {
        logger = logger.getLogger("FamilyMapServer");
    }

    private final static String DIRECTORY = "web";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.entering("FileHandler", "handle");
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                String url = exchange.getRequestURI().getPath();
                if (url.length() == 0 || url.equals("/")) {
                    url = "/index.html";
                }
                String path = DIRECTORY + url;
                File file = new File(path);
                System.out.println(path);
                if (file.exists() && file.canRead()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream response = exchange.getResponseBody();
                    Files.copy(file.toPath(), response);
                    response.close();
                    success = true;
                    logger.fine("Successfully returned " + path);
                    System.out.println("Successfully returned " + path);
                    success = true;
                }
            }
            if (!success) {
                logger.fine("Failed to return resource");
                System.out.println("Failed to return resource");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            logger.severe("IOException in FileHandler.handle: " + e.getMessage());
            System.out.println("IOException in FileHandler.handle: " + e.getMessage());
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
        finally {
            logger.exiting("FileHandler", "handle");
        }
    }
}
