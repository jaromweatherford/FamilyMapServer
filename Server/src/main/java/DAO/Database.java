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
    private static Database _instance;
    private Connection connection = null;
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

    public static Database instance() throws DatabaseException {
        if (_instance == null) {
            _instance = new Database();
            _instance.openConnection();
        }
        return _instance;
    }

    private Database() {
    }

    /** openConnection prepares the database to read or make changes
     *
     */
    private void openConnection() throws DatabaseException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:FamilyMapServer.db";

            if (connection == null) {
                connection = DriverManager.getConnection(CONNECTION_URL);

                connection.setAutoCommit(false);
            }

            userDAO = new UserDAO(connection);
            personDAO = new PersonDAO(connection);
            eventDAO = new EventDAO(connection);
            tokenDAO = new TokenDAO(connection);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("openConnectionFailed");
        }
    }

    /** commits or rolls back
     *
     */
    public void commit(boolean commit) throws DatabaseException {
        try {
            if (connection != null) {
                if (commit) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("closeConnection failed");
        }
    }

    /** closeConnection closes the connection
     *
     */
    private void closeConnection(boolean commit) throws DatabaseException {
        try {
            if (connection != null) {
                if (commit) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
                connection.close();
                connection = null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("closeConnection failed");
        }
    }

    @Override
    public void finalize() {
        try {
            closeConnection(false);
        }
        catch (Exception e) {
            e.printStackTrace();
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
