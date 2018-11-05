package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import Model.AuthToken;
import Model.User;

/** TokenDAO allows and handles database access for AuthToken objects
 *
 * @author jarom
 * @version 0.0
 */
public class TokenDAO {
    Connection connection;

    public TokenDAO(Connection connection) {
        this.connection = connection;
    }

    /** create adds the passed token to the database, also adds its relations to the database
     *
     * @param token             The token to be added to the database
     * @return                  false if the update failed, true if it succeeded
     */
    public boolean create(AuthToken token) {
        if (connection == null) {
            return false;
        }
        String sql = "INSERT INTO AuthTokens(TokenCode, UserID) VALUES(?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token.getCode());
            statement.setString(2, token.getUserName());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** read takes a UUID and returns a token with that UUID as its ID, or NULL if it doesn't exist
     *
     * @param code              The code of the token to be returned
     * @return                  The token with that code, or NULL if no token has that ID
     */
    public AuthToken read(String code) {
        String sql = "SELECT * FROM AuthTokens WHERE TokenCode = ?";
        AuthToken token = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, code);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                token = new AuthToken();
                readToken(rs, token);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }

    /** read takes a user and returns all tokens related to that user, the list will be empty
     * if the user is not found or if no tokens are found for them
     *
     * @param user              The user who's tokens are desired
     * @return                  A list of all tokens found to relate to that user
     */
    public ArrayList<AuthToken> read(User user) {

        String sql = "SELECT * FROM AuthTokens WHERE UserID = ?";
        ArrayList<AuthToken> tokens = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                AuthToken token = new AuthToken();
                readToken(rs, token);
                tokens.add(token);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return tokens;
    }

    /** destroy takes a token and removes it from the database
     *
     * @param token             The user to be removed
     * @return                  false if the deletion failed, true if it succeeded
     */
    public boolean destroy(AuthToken token) {
        String sql = "DELETE FROM AuthTokens WHERE TokenCode = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token.getCode());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void readToken(ResultSet rs, AuthToken token) {
        try {
            token.setCode(rs.getString("TokenCode"));
            token.setUserName(rs.getString("UserID"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
