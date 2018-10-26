package DAO;

import java.sql.Connection;

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

    /** openConnection prepares the database to read or make changes
     *
     */
    void openConnection() {

    }

    /** closeConnection closes the connection
     *
     */
    void closeConnection() {

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
