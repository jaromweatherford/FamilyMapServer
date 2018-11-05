package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        if (connection == null) {
            return false;
        }
        String sql = "INSERT INTO Users(UserName, Password, Email, FirstName, LastName, Gender, PersonID) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());
            statement.setString(6, Character.toString(user.getGender()));
            statement.setString(7, user.getPersonID());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** read takes a username and returns a user with that username, or NULL if it doesn't exist
     *
     * @param userName          The username of the user to be returned
     * @return                  The user with that name, or NULL if no user has that name
     */
    public User read(String userName) {
        String sql = "SELECT * FROM Users WHERE UserName = ?";
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = new User();
                readUser(rs, user);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    /** read returns all users found in the database, the list is empty if none are found
     *
     * @return                  A list of all users in the database
     */
    public ArrayList<User> read() {
        String sql = "SELECT * FROM Users";
        ArrayList<User> users = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                readUser(rs, user);
                users.add(user);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return users;
    }

    /** destroy takes an user and removes it from the database, also removes all relations
     *
     * @param user              The user to be removed
     * @return                  false if the deletion failed, true if it succeeded
     */
    public boolean destroy(User user) {
        String sql = "DELETE FROM Users WHERE UserName = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void readUser(ResultSet rs, User user) {
        try {
            user.setUserName(rs.getString("UserName"));
            user.setPassword(rs.getString("Password"));
            user.setEmail(rs.getString("Email"));
            user.setFirstName(rs.getString("FirstName"));
            user.setLastName(rs.getString("LastName"));
            user.setGender(rs.getString("Gender").charAt(0));
            user.setPersonID(rs.getString("PersonID"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
