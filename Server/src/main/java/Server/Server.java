package Server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

import Handler.ClearHandler;
import Handler.EventHandler;
import Handler.FileHandler;
import Handler.FillHandler;
import Handler.LoadHandler;
import Handler.LoginHandler;
import Handler.PersonHandler;
import Handler.RegisterHandler;

/**
 * Created by jaromwea on 10/26/18.
 */

public class Server {

    private static Logger logger;

    static {
        logger = Logger.getLogger("FamilyMapServer");
    }

    private static final int MAX_CONNECTIONS = 12;
    private HttpServer server;

    private void run(String portNumber) {

        logger.entering("Server", "run");

        try {
            logger.fine("Creating Server");
            System.out.println("Creating Server");
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)), MAX_CONNECTIONS);
        }
        catch (IOException e) {
            logger.severe("Failed to Create Server");
            System.out.println("Failed to Create Server");
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        logger.fine("Creating Contexts");
        System.out.println("Creating Contexts");

        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person/", new PersonHandler());
        server.createContext("/event/", new EventHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/", new FileHandler());

        logger.fine("Starting Server");
        System.out.println("Starting Server");

        server.start();
        logger.exiting("Server", "run");
    }

    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
