package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** Database serves a container for all the DAOs
 *
 * @author jarom
 * @version 0.0
 */
public class Database {
    private Connection connection;
    private UserDAO userDAO;
    private PersonDAO personDAO;
    private EventDAO eventDAO;
    private TokenDAO tokenDAO;

    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** openConnection prepares the database to read or make changes
     *
     */
    public void openConnection() throws DatabaseException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:FamilyMapServer.db";

            connection = DriverManager.getConnection(CONNECTION_URL);

            connection.setAutoCommit(false);

            userDAO = new UserDAO(connection);
            personDAO = new PersonDAO(connection);
            eventDAO = new EventDAO(connection);
            tokenDAO = new TokenDAO(connection);
        }
        catch (SQLException e) {
            throw new DatabaseException("openConnectionFailed");
        }
    }

    /** closeConnection closes the connection
     *
     */
    public void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (commit) {
                connection.commit();
            }
            else {
                connection.rollback();
            }
            connection.close();
            connection = null;

            userDAO = null;
            personDAO = null;
            eventDAO = null;
            tokenDAO = null;
        }
        catch (SQLException e) {
            throw new DatabaseException("closeConnection failed");
        }
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public TokenDAO getTokenDAO() {
        return tokenDAO;
    }
}
