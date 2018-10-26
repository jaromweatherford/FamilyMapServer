package DAO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.UUID;

import Model.User;

/** UserDAO allows and handles database access for User objects
 *
 * @author jarom
 * @version 0.0
 */
public class UserDAO {
    Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * create adds the passed user to the database
     * @param user              The user to be added to the database
     * @return                  false if the update failed, true if it succeeded
     */
    public boolean create(User user) {
        return false;
    }

    /** read takes a username and returns a user with that username, or NULL if it doesn't exist
     *
     * @param ID                The username of the user to be returned
     * @return                  The user with that name, or NULL if no user has that name
     */
    public User read(String userName) {
        return null;
    }

    /** read returns all users found in the database, the list is empty if none are found
     *
     * @return                  A list of all users in the database
     */
    public ArrayList<User> read() {
        return new ArrayList<>();
    }

    /** destroy takes an user and removes it from the database, also removes all relations
     *
     * @param user              The user to be removed
     * @return                  false if the deletion failed, true if it succeeded
     */
    public boolean destroy(User user) {
        return false;
    }
}
